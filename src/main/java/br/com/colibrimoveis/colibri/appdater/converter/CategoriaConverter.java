package br.com.colibrimoveis.colibri.appdater.converter;

import java.beans.PropertyEditorSupport;

import br.com.colibrimoveis.colibri.appdater.DAO.CategoriaDAO;
import br.com.colibrimoveis.colibri.appdater.model.Categoria;

public class CategoriaConverter extends PropertyEditorSupport{
	
	private CategoriaDAO dao;
	
	public CategoriaConverter(CategoriaDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id = new Long(text);
		Categoria categoria = dao.findOne(id);
		super.setValue(categoria);
	}
	
}
