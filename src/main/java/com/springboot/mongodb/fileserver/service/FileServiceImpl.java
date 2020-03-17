package com.springboot.mongodb.fileserver.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.springboot.mongodb.fileserver.domain.File;
import com.springboot.mongodb.fileserver.repository.FileRepository;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	public FileRepository fileRepository;

	@Autowired
	private GridFsTemplate gridFsTemplate;

	@Autowired
	private GridFsOperations operations;

//	@Autowired
//	MongoTemplate mongoTemplate;

	@Override
	public List<File> listFilesByPage(int pageIndex, int pageSize) {
		Page<File> page = null;
		List<File> list = null;

		Sort sort = Sort.by(Direction.DESC, "uploadDate");
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

//	@Override
//	 public String saveBigFile(MultipartFile file) throws IOException {
//	        DBObject metaData = new BasicDBObject();
//	        metaData.put("Type", "BigFile");
////	        metaData.put("title", file.getName());
//	        ObjectId id = gridFsTemplate.store(file.getInputStream(), file.getName(), file.getContentType(), metaData);
//	        fileRepository.save(file);
//	        
//	        return id.toString();
//	        
//	    }
}
