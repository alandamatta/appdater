package br.com.colibrimoveis.colibri.appdater.converter;

import java.beans.PropertyEditorSupport;

import br.com.colibrimoveis.colibri.appdater.DAO.CaracteristicaDAO;
import br.com.colibrimoveis.colibri.appdater.model.Caracteristica;

public class CaracteristicaConverter extends PropertyEditorSupport{
	
	private CaracteristicaDAO dao;
	
	public CaracteristicaConverter(CaracteristicaDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id = new Long(text);
		Caracteristica caracteristica = dao.findOne(id);
		super.setValue(caracteristica);
	}

}
