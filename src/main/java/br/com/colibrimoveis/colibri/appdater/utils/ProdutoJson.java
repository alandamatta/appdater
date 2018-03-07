package br.com.colibrimoveis.colibri.appdater.utils;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
public class ProdutoJson {
	
	private String id;
	private String codigo;
	private String largura;
	private String profundidade;
	private String altura;
	private String volumes;
	private String peso;
	private String nome;
	private String foto;
	private List<FotoJson> fotos = new ArrayList<>();
	private List<CorJson> cores = new ArrayList<>();
	private List<AmbienteJson> ambientes = new ArrayList<>();
	private List<CaracteristicaJson> caracteristicas = new ArrayList<>();
	private List<CategoriaJson> categorias = new ArrayList<>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getLargura() {
		return largura;
	}
	public void setLargura(String largura) {
		this.largura = largura;
	}
	public String getProfundidade() {
		return profundidade;
	}
	public void setProfundidade(String profundidade) {
		this.profundidade = profundidade;
	}
	public String getAltura() {
		return altura;
	}
	public void setAltura(String altura) {
		this.altura = altura;
	}
	public String getVolumes() {
		return volumes;
	}
	public void setVolumes(String volumes) {
		this.volumes = volumes;
	}
	public String getPeso() {
		return peso;
	}
	public void setPeso(String peso) {
		this.peso = peso;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public List<FotoJson> getFotos() {
		return fotos;
	}
	public void setFotos(List<FotoJson> fotos) {
		this.fotos = fotos;
	}
	public List<CorJson> getCores() {
		return cores;
	}
	public List<AmbienteJson> getAmbientes() {
		return ambientes;
	}
	public void setAmbientes(List<AmbienteJson> ambientes) {
		this.ambientes = ambientes;
	}
	public void setCores(List<CorJson> cores) {
		this.cores = cores;
	}
	public List<CaracteristicaJson> getCaracteristicas() {
		return caracteristicas;
	}
	public void setCaracteristicas(List<CaracteristicaJson> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	public List<CategoriaJson> getCategorias() {
		return categorias;
	}
	public void setCategorias(List<CategoriaJson> categorias) {
		this.categorias = categorias;
	}
}
