package br.com.colibrimoveis.colibri.appdater.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.colibrimoveis.colibri.appdater.model.Categoria;

public interface CategoriaDAO extends JpaRepository<Categoria, Long>{

}
