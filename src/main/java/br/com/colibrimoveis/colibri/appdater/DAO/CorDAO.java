package br.com.colibrimoveis.colibri.appdater.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.colibrimoveis.colibri.appdater.model.Cor;

public interface CorDAO extends JpaRepository<Cor, Long>{
}
