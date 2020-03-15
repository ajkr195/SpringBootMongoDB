package com.springboot.mongodb.fileserver.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.springboot.mongodb.fileserver.domain.File;
import com.springboot.mongodb.fileserver.repository.FileRepository;

@Service
public class FileServiceImpl implements FileService {
	
	@Autowired
	public FileRepository fileRepository;

	@Override
	public List<File> listFilesByPage(int pageIndex, int pageSize) {
		Page<File> page = null;
		List<File> list = null;
		
		Sort sort = Sort.by(Direction.DESC,"uploadDate"); 
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
		
		page = fileRepository.findAll(pageable);
		list = page.getContent();
		return list;
	}
	
	@Override
	public File saveFile(File file) {
		return fileRepository.save(file);
	}


	@Override
	public Optional<File> getFileById(String id) {
		return fileRepository.findById(id);
	}

	@Override
	public void deleteFile(String id) {
		fileRepository.deleteById(id);
	}
}
