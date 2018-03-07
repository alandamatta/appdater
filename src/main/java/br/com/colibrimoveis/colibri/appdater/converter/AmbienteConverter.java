package br.com.colibrimoveis.colibri.appdater.converter;

import java.beans.PropertyEditorSupport;

import br.com.colibrimoveis.colibri.appdater.DAO.AmbienteDAO;
import br.com.colibrimoveis.colibri.appdater.model.Ambiente;

public class AmbienteConverter extends PropertyEditorSupport{
	
	private AmbienteDAO dao;
	
	public AmbienteConverter(AmbienteDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id = new Long(text);
		Ambiente ambiente = dao.findOne(id);
		super.setValue(ambiente);
	}

}
