package br.com.colibrimoveis.colibri.appdater.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GeraPacoteApp {

	private String dirPadraoProj = "";
	private String dirPastaJson = "";
	private String dirPastaImg = "";
	private String dirAtualizacao = "";
	
	public void play(AtualizacaoJson atualizacaoJson, String nomeAtualizacao, String dirPadraoProj){
		this.dirPadraoProj = dirPadraoProj; 
		criarDirAtualizacao(nomeAtualizacao);
		criarJson(atualizacaoJson);
		transferirImagens(atualizacaoJson);
	}
	
	private void criarJson(AtualizacaoJson atualizacaoJson) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(dirPastaJson+File.separator+"atualizacao.json"), atualizacaoJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String criarDirAtualizacao(String nomeAtualizacao){
		dirPadraoProj =  System.getProperty("user.dir");
		dirAtualizacao = dirPadraoProj + File.separator + "atualizacao" + File.separator + nomeAtualizacao;
		File pasta = new File(dirAtualizacao);
		pasta.mkdirs();
		criarPastaJson(dirAtualizacao);
		criarPastaImg(dirAtualizacao);
		return dirAtualizacao;
	}
	
	private void criarPastaJson(String dirAtualizacao){
		dirPastaJson = dirAtualizacao + File.separator + "json";
		File pasta = new File(dirPastaJson);
		pasta.mkdir();
	}
	
	private void criarPastaImg(String dirAtualizacao){
		dirPastaImg = dirAtualizacao + File.separator + "img";
		File pasta = new File(dirPastaImg);
		pasta.mkdir();
	}
	
	private void transferirImagens(AtualizacaoJson atualizacaoJson){
		atualizacaoJson.getProdutosJson().forEach(produto -> {
			produto.getFotos().forEach(foto -> {
				Path origem = Paths.get(dirPadraoProj + File.separator + foto.getDiretorio());
				if(origem.toFile().exists()) {
					Path destino = Paths.get(dirAtualizacao + File.separator +  foto.getDiretorio());
					File file = new File(destino.toString());
					try {
						if(!file.exists())
							Files.copy(origem, destino);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		});
	}
}
