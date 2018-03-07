package br.com.colibrimoveis.colibri.appdater.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Atualizacao {

	private Long id;
	private String nome;
	private Date data;
	private List<ProdutoEAtualizacao> produtos;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotNull(message="É necessário uma descrição")
	@NotBlank(message="É necessário uma descrição")
	@Length(min=5, max=30, message="A descrição deve conter entre 5 e 30 caracteres")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	@OneToMany(mappedBy="atualizacao", fetch=FetchType.EAGER)
	public List<ProdutoEAtualizacao> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<ProdutoEAtualizacao> produtos) {
		this.produtos = produtos;
	}
	
	@Override
	public String toString() {
		return "Atualizacao [id=" + id + ", nome=" + nome + ", data=" + data + "]";
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
		Atualizacao other = (Atualizacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
