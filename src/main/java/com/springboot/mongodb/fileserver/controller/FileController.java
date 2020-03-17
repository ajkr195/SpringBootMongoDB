package com.springboot.mongodb.fileserver.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.mongodb.fileserver.domain.File;
import com.springboot.mongodb.fileserver.service.FileService;
import com.springboot.mongodb.fileserver.util.MD5Util;

@CrossOrigin(origins = "*", maxAge = 7200)
@Controller
public class FileController {

	@Autowired
	private FileService fileService;

	@Value("${server.address}")
	private String serverAddress;

	@Value("${server.port}")
	private String serverPort;

	@RequestMapping(value = "/")
	public String index(Model model) {
		model.addAttribute("files", fileService.listFilesByPage(0, 20));
		return "index";
	}

	@GetMapping("files/{pageIndex}/{pageSize}")
	@ResponseBody
	public List<File> listFilesByPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
		return fileService.listFilesByPage(pageIndex, pageSize);
	}

	@GetMapping("files/{id}")
	@ResponseBody
	public ResponseEntity<Object> serveFile(@PathVariable String id) throws UnsupportedEncodingException {

		Optional<File> file = fileService.getFileById(id);

		if (file.isPresent()) {
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; fileName=" + new String(file.get().getName().getBytes("utf-8"), "ISO-8859-1"))
					.header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
					.header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("Connection", "close")
					.body(file.get().getContent().getData());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
		}

	}

	@GetMapping("/view/{id}")
	@ResponseBody
	public ResponseEntity<Object> serveFileOnline(@PathVariable String id) {

		Optional<File> file = fileService.getFileById(id);

		if (file.isPresent()) {
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + file.get().getName() + "\"")
					.header(HttpHeaders.CONTENT_TYPE, file.get().getContentType())
					.header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("Connection", "close")
					.body(file.get().getContent().getData());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
		}

	}

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model) {

		try {
			File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(),
					new Binary(file.getBytes()));
			f.setMd5(MD5Util.getMD5(file.getInputStream()));

//			Path path = Paths.get(file.getName());
//			long expectedSizeInMB = 16;
//			long expectedSizeInBytes = 1024 * 1024 * expectedSizeInMB;
//			long sizeInBytes = -1;
//			try {
//			    sizeInBytes = Files.size(path);
//			    System.out.println(">>>>>>>>>>>>         \n\n" +  sizeInBytes + "\n\n>>>>>>>>>>>>        ");
//			} catch (IOException e) {
//			    System.err.println("Cannot get the size - " + e);
//			}

//			if (sizeInBytes > expectedSizeInBytes) {
//			    System.out.println("Bigger than " + expectedSizeInMB + " MB. So storing file using GridFS.\"");
//			    fileService.saveBigFile((MultipartFile) f);
//			} else {
//			    System.out.println("Not bigger than " + expectedSizeInMB + " MB. So storing file as BSON.");
//			    fileService.saveFile(f);
//			}

			fileService.saveFile(f);

		} catch (IOException | NoSuchAlgorithmException ex) {
			ex.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "Your " + file.getName() + " is wrong!");
			return "redirect:/";
		}

		redirectAttributes.addFlashAttribute("message", "somemessage");

		return "redirect:/";
	}

	@PostMapping("/upload")
	@ResponseBody
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
		File returnFile = null;
		try {
			File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(),
					new Binary(file.getBytes()));
			f.setMd5(MD5Util.getMD5(file.getInputStream()));
			returnFile = fileService.saveFile(f);
			String path = "//" + serverAddress + ":" + serverPort + "/view/" + returnFile.getId();
			return ResponseEntity.status(HttpStatus.OK).body(path);

		} catch (IOException | NoSuchAlgorithmException ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}

	}

	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public ResponseEntity<String> deleteFile(@PathVariable String id) {

		try {
//			System.out.println("Deleting File id - " + id);
			fileService.deleteFile(id);
			return ResponseEntity.status(HttpStatus.OK).body("DELETE Success!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
