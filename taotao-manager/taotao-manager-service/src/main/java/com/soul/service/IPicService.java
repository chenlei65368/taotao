package com.soul.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface IPicService {
	
	Map uploadPicture(MultipartFile file) ;
}
