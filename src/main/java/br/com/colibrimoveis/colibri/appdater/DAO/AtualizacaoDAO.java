package br.com.colibrimoveis.colibri.appdater.DAO;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.colibrimoveis.colibri.appdater.model.Atualizacao;

public interface AtualizacaoDAO extends JpaRepository<Atualizacao, Long>{
	
}
