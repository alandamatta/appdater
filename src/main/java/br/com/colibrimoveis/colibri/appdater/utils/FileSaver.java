package br.com.colibrimoveis.colibri.appdater.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileSaver {
	
	@Autowired
	private HttpServletRequest request;
	
	public String writeSingle(String baseFolder, MultipartFile file){
		String realPath = request.getServletContext().getRealPath("/"+baseFolder);
		
		try {
			String path = realPath + "/" + file.getOriginalFilename();
			file.transferTo(new File(path));
			return baseFolder + "/" + file.getOriginalFilename();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<String> writeMultiple(String baseFolder, List<MultipartFile> files){
		String realPath = request.getServletContext().getRealPath("/"+baseFolder);
		List<String> nomeArquivos = new ArrayList<>();
		for (MultipartFile file : files) {
			try {
				String path = realPath + "/" + file.getOriginalFilename();
				file.transferTo(new File(path));
				nomeArquivos.add(baseFolder + "/" + file.getOriginalFilename());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return nomeArquivos;
	}
}
