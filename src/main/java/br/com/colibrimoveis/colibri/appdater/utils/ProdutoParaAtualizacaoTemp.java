package br.com.colibrimoveis.colibri.appdater.utils;

import org.springframework.stereotype.Component;

import br.com.colibrimoveis.colibri.appdater.model.Produto;

@Component
public class ProdutoParaAtualizacaoTemp{
	
	private Produto produto;
	private boolean atualizacao;
	
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public boolean isAtualizacao() {
		return atualizacao;
	}
	public void setAtualizacao(boolean atualizacao) {
		this.atualizacao = atualizacao;
	}
	
}
