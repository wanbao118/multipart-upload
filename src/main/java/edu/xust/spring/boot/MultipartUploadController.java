package edu.xust.spring.boot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("multipart")
public class MultipartUploadController {

private String result = "{\"errorMsg\":\"%s\"}";

	private static final Logger log = LoggerFactory.getLogger(MultipartUploadController.class);

	@ResponseBody
	@RequestMapping(value="upload", method=RequestMethod.POST)
	public Object upload(@RequestParam String key, HttpServletRequest request) throws Exception {
		try {
			log.info("request: {}", request);
			Part part = request.getPart("file");
			InputStream fis = part != null ? part.getInputStream() : request.getInputStream();
			String fileName = part != null ? part.getSubmittedFileName() : key;
			log.info("file name: {}", fileName);
			File file = new File(System.getProperty("user.home") + "/upload/" + fileName);
			log.info("file path: {}", file.getAbsolutePath());
			saveFile(fis, file);
		} catch(Exception e) {
			return String.format(result, e.getMessage());
		}
		return String.format(result, "success");
	}
	
	public void saveFile(InputStream ins,File file) throws IOException{  
	   OutputStream os = new FileOutputStream(file);  
	   int bytesRead = 0;  
	   byte[] buffer = new byte[8192];  
	   while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {  
	      os.write(buffer, 0, bytesRead);  
	   }  
	   os.close();  
	   ins.close();  
	} 
}
