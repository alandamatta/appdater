package br.com.colibrimoveis.colibri.appdater.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Produto implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=false)
	private boolean situacao;
	@Column(nullable=false)
	@NotNull(message="Código é obrigatório")
	@NotBlank(message="Código é obrigatório")
	private String codigo;
	@NotNull(message="Largura é obrigatória")
	@Min(value=1, message="Largura deve ser no mínimo 1")
	@Max(value=99999, message="Largura deve ser no máximo 99999")
	@Column(nullable=false)
	private Long largura;
	@NotNull(message="Altura é obrigatória")
	@Min(value=1, message="Altura deve ser no mínimo 1")
	@Max(value=99999, message="Altura deve ser no máximo 99999")
	@Column(nullable=false)
	private Long altura;
	@NotNull(message="Profundidade é obrigatória")
	@Min(value=1, message="Profundidade deve ser no mínimo 1")
	@Max(value=99999, message="Profundidade deve ser no máximo 99999")
	@Column(nullable=false)
	private Long profundidade;
	@NotNull(message="Volume é obrigatório")
	@Min(value=1, message="Volume deve ser no mínimo 1")
	@Max(value=99999, message="Volume deve ser no máximo 99999")
	@Column(nullable=false)
	private Integer volumes;
	@NotNull(message="Peso é obrigatório")
	@Min(value=1, message="Peso deve ser no mínimo 1")
	@Max(value=99999, message="Peso deve ser no máximo 99999")
	@Column(nullable=false)
	private Double peso;
	@NotNull(message="Nome é obrigatório")
	@NotBlank(message="Nome é obrigatório")
	@Length(min=4, max=30, message="Nome deve conter entre 4 e 30 caracteres")
	@Column(nullable=false)
	private String nome;
	private String fotoPrincipal;
	@OneToMany(mappedBy="produto", fetch=FetchType.EAGER)
	private List<FotosDoProduto> fotos;
	
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="coresDoProduto", joinColumns= @JoinColumn(name="produto_id"),
		inverseJoinColumns= @JoinColumn(name="cor_id"))
	@NotEmpty(message="É necessário ao menos uma cor")
	private Set<Cor> cores;
	
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="caracteristicasDoProduto", 
		joinColumns= @JoinColumn(name="produto_id"),
		inverseJoinColumns= @JoinColumn(name="caracteristica_id"))
	@NotEmpty(message="É necessário ao menos uma característica")
	private Set<Caracteristica> caracteristicas;
	
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="categoriasDoProduto", 
		joinColumns= @JoinColumn(name="produto_id"),
		inverseJoinColumns= @JoinColumn(name="categoria_id"))
	@NotEmpty(message="É necessário ao menos uma categoria")
	private Set<Categoria> categorias;
	
	public Long getId() {	
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isSituacao() {
		return situacao;
	}
	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Long getLargura() {
		return largura;
	}
	public void setLargura(Long largura) {
		this.largura = largura;
	}
	public Long getAltura() {
		return altura;
	}
	public void setAltura(Long altura) {
		this.altura = altura;
	}
	public Long getProfundidade() {
		return profundidade;
	}
	public void setProfundidade(Long profundidade) {
		this.profundidade = profundidade;
	}
	public Integer getVolumes() {
		return volumes;
	}
	public void setVolumes(Integer volumes) {
		this.volumes = volumes;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFotoPrincipal() {
		return fotoPrincipal;
	}
	public void setFotoPrincipal(String fotoPrincipal) {
		this.fotoPrincipal = fotoPrincipal;
	}
	public List<FotosDoProduto> getFotos() {
		return fotos;
	}
	public void setFotos(List<FotosDoProduto> fotos) {
		this.fotos = fotos;
	}
	public Set<Cor> getCores() {
		return cores;
	}
	public void setCores(Set<Cor> cores) {
		this.cores = cores;
	}
	public Set<Caracteristica> getCaracteristicas() {
		return caracteristicas;
	}
	public void setCaracteristicas(Set<Caracteristica> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	public Set<Categoria> getCategorias() {
		return categorias;
	}
	public void setCategorias(Set<Categoria> categorias) {
		this.categorias = categorias;
	}
	
	
	
	
	@Override
	public String toString() {
		return "Produto [id=" + id + ", situacao=" + situacao + ", codigo=" + codigo + ", largura=" + largura
				+ ", altura=" + altura + ", profundidade=" + profundidade + ", volumes=" + volumes + ", peso=" + peso
				+ ", nome=" + nome + ", fotoPrincipal=" + fotoPrincipal + ", fotos=" + fotos + ", cores=" + cores
				+ ", caracteristicas=" + caracteristicas + ", categorias=" + categorias + "]";
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
