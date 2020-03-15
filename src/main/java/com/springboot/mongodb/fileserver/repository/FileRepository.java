package com.springboot.mongodb.fileserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.springboot.mongodb.fileserver.domain.File;

public interface FileRepository extends MongoRepository<File, String> {
}
