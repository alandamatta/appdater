package br.com.colibrimoveis.colibri.appdater;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.colibrimoveis.colibri.appdater.DAO.ProdutoDAO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ColibriAppdaterApplicationTests {

	@Autowired
	ProdutoDAO produtoDao;
	
	@Autowired
	HttpServletRequest request;
	
	@Test
	public void contextLoads() {
		
		System.out.println(request.getServletContext().getRealPath("\\"));
		
	}
	
}
