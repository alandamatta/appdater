package br.com.colibrimoveis.colibri.appdater.utils;

import java.util.ArrayList;
import java.util.List;

public class AtualizacaoJson {
	
	List<ProdutoJson> produtos = new ArrayList<>();
	
	public List<ProdutoJson> getProdutosJson() {
		return produtos;
	}
	public void setProdutosJson(List<ProdutoJson> produtosJson) {
		this.produtos = produtosJson;
	}
	
}
