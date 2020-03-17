package com.springboot.mongodb.fileserver.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.mongodb.fileserver.domain.File;

public interface FileService {

	File saveFile(File file);
	
	List<File> listFilesByPage(int pageIndex, int pageSize);

	Optional<File> getFileById(String id);

	void deleteFile(String id);

//	String saveBigFile(MultipartFile file) throws IOException;

}
