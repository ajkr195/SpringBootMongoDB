package com.springboot.mongodb.fileserver.domain;

import java.util.Date;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class File {
	@Id
	private String id;
	private String name;
	private String contentType;
	private long size;
	private Date uploadDate;
	private String md5;
	private Binary content;
	private String path;
	
	private String description;
	private String docclassname;
	private String ownername;
	private String createdby;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public Binary getContent() {
		return content;
	}

	public void setContent(Binary content) {
		this.content = content;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocclassname() {
		return docclassname;
	}

	public void setDocclassname(String docclassname) {
		this.docclassname = docclassname;
	}

	public String getOwnername() {
		return ownername;
	}

	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public File() {
	}

	public File(String name, String contentType, long size, Binary content) {
		this.name = name;
		this.contentType = contentType;
		this.size = size;
		this.uploadDate = new Date();
		this.content = content;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		File fileInfo = (File) object;
		return java.util.Objects.equals(size, fileInfo.size) && java.util.Objects.equals(name, fileInfo.name)
				&& java.util.Objects.equals(contentType, fileInfo.contentType)
				&& java.util.Objects.equals(uploadDate, fileInfo.uploadDate)
				&& java.util.Objects.equals(md5, fileInfo.md5) && java.util.Objects.equals(id, fileInfo.id);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(name, contentType, size, uploadDate, md5, id);
	}

	@Override
	public String toString() {
		return "File{" + "name='" + name + '\'' + ", contentType='" + contentType + '\'' + ", size=" + size
				+ ", uploadDate=" + uploadDate + ", md5='" + md5 + '\'' + ", id='" + id + '\'' + '}';
	}
}
