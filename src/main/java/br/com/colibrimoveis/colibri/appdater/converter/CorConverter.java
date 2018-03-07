package br.com.colibrimoveis.colibri.appdater.converter;

import java.beans.PropertyEditorSupport;

import br.com.colibrimoveis.colibri.appdater.DAO.CorDAO;
import br.com.colibrimoveis.colibri.appdater.model.Cor;

public class CorConverter extends PropertyEditorSupport{
	
	private CorDAO dao;
	
	public CorConverter(CorDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id = new Long(text);
		Cor cor = dao.findOne(id);
		super.setValue(cor);
	}

}
