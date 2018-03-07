package br.com.colibrimoveis.colibri.appdater.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.colibrimoveis.colibri.appdater.model.Produto;

public interface ProdutoDAO extends JpaRepository<Produto, Long>{
	@Query("select p from Produto p where p.situacao = true")
	List<Produto> findBySituacao();
	
}
