package br.com.colibrimoveis.colibri.appdater.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.colibrimoveis.colibri.appdater.model.FotosDoProduto;
import br.com.colibrimoveis.colibri.appdater.model.Produto;

public interface FotosDoProdutoDAO extends JpaRepository<FotosDoProduto, Long>{
	@Query("select f from FotosDoProduto f where f.produto = ?1")
    List<FotosDoProduto> findByProduto(Produto produto);
}

