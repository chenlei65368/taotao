package com.soul.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.soul.common.utils.FtpUtil;
import com.soul.common.utils.IDUtils;
import com.soul.service.IPicService;

@Service
@Transactional
public class PicServiceImpl implements IPicService {

	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP_PASSWD}")
	private String FTP_PASSWD;
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;
	

	@Override
	public Map uploadPicture(MultipartFile uploadFile) {
		
		Map resultMap = new HashMap<>();
		try {
			String oldName = uploadFile.getOriginalFilename();

			String newName = IDUtils.genImageName();
			newName = newName + oldName.substring(oldName.lastIndexOf("."));
			
			String imagePath = new DateTime().toString("/yyyy/MM/dd");
			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWD, FTP_BASE_PATH,
					imagePath, newName, uploadFile.getInputStream());
			
			if(result) {
				resultMap.put("error", 0);
				resultMap.put("url", IMAGE_BASE_URL+imagePath+"/"+newName);
				
			}else {
				resultMap.put("error", 1);
				resultMap.put("message", "文件上传失败！");
			}
		} catch (IOException e) {
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传失败！");
		}
		return resultMap;
	}

}
