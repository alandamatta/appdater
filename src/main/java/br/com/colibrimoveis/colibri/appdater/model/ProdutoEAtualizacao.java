package br.com.colibrimoveis.colibri.appdater.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ProdutoEAtualizacao {
	
	private Long id;
	private Atualizacao atualizacao;
	private Produto produto;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	public Atualizacao getAtualizacao() {
		return atualizacao;
	}
	public void setAtualizacao(Atualizacao atualizacao) {
		this.atualizacao = atualizacao;
	}
	
	@ManyToOne
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	@Override
	public String toString() {
		return "ProdutoEAtualizacao [id=" + id + ", atualizacao=" + atualizacao + ", produto=" + produto + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdutoEAtualizacao other = (ProdutoEAtualizacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
