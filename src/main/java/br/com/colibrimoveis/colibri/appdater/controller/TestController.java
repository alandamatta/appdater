package br.com.colibrimoveis.colibri.appdater.controller;

import java.util.List;

import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import br.com.colibrimoveis.colibri.appdater.utils.FileSaver;
/*
 * Classe somente para testes, não usada em PRODUÇÃO
 * 
 * 
 * 
 */
@Controller
@RequestMapping("/upload")
public class TestController {
	@Autowired
	private FileSaver filesaver;
	
	@GetMapping
	public String init(){
		return "teste/upload";
	}
	
	@RequestMapping(path="/salvar", method=RequestMethod.POST)
	public String salvar(List<MultipartFile> summarys){
		//String webPath = filesaver.writeSingle("upload-images", summary);
		filesaver.writeMultiple("upload-images", summarys).forEach(s -> {System.out.println();});
		return "teste/upload";
	}
}
