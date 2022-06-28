package com.surajrai.blog.backendapi.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.surajrai.blog.backendapi.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadFile(String path, MultipartFile file) throws IOException {
		
		String originalFilename = file.getOriginalFilename();
		String randomFileName = UUID.randomUUID().toString();
		
		randomFileName=randomFileName+originalFilename.substring(originalFilename.lastIndexOf("."));
		
		String fullPath=path+File.separator+randomFileName;
		File f=new File(path);
		
		if(!f.exists())
			f.mkdir();
		
		Files.copy(file.getInputStream(), Paths.get(fullPath));
		
		return randomFileName;
	}

	@Override
	public InputStream getFile(String path, String fileName) throws FileNotFoundException {
		String fullFileName=path+File.separator+fileName;
		InputStream stream=new FileInputStream(fullFileName);
		return stream;
	}

}
