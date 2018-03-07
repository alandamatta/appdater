package br.com.colibrimoveis.colibri.appdater.controller;

import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.colibrimoveis.colibri.appdater.DAO.AmbienteDAO;
import br.com.colibrimoveis.colibri.appdater.DAO.CaracteristicaDAO;
import br.com.colibrimoveis.colibri.appdater.DAO.CategoriaDAO;
import br.com.colibrimoveis.colibri.appdater.DAO.CorDAO;
import br.com.colibrimoveis.colibri.appdater.DAO.FotosDoProdutoDAO;
import br.com.colibrimoveis.colibri.appdater.DAO.ProdutoDAO;
import br.com.colibrimoveis.colibri.appdater.converter.CaracteristicaConverter;
import br.com.colibrimoveis.colibri.appdater.converter.CategoriaConverter;
import br.com.colibrimoveis.colibri.appdater.converter.CorConverter;
import br.com.colibrimoveis.colibri.appdater.model.Caracteristica;
import br.com.colibrimoveis.colibri.appdater.model.Categoria;
import br.com.colibrimoveis.colibri.appdater.model.Cor;
import br.com.colibrimoveis.colibri.appdater.model.FotosDoProduto;
import br.com.colibrimoveis.colibri.appdater.model.Produto;
import br.com.colibrimoveis.colibri.appdater.utils.FileSaver;

@Controller
@RequestMapping(path="/produto")
public class ProdutoController {
	
	private FotosDoProduto fotosDoProduto = new FotosDoProduto();
	@Autowired
	private ProdutoDAO produtoDAO;
	@Autowired
	private AmbienteDAO ambienteDAO;
	@Autowired
	private CategoriaDAO categoriaDAO;
	@Autowired 
	private FotosDoProdutoDAO fotosDoProdutoDAO;
	@Autowired
	private CaracteristicaDAO caracteristicaDAO;
	@Autowired
	private CorDAO corDAO;
	@Autowired
	private FileSaver fileSaver;
	
	private List<FotosDoProduto> fotos;
	private List<String> erros;
	
	@InitBinder
	protected void initBinder(HttpServletRequest http, ServletRequestDataBinder binder) throws Exception{
		binder.registerCustomEditor(Categoria.class, new CategoriaConverter(categoriaDAO));
		binder.registerCustomEditor(Caracteristica.class, new CaracteristicaConverter(caracteristicaDAO));
		binder.registerCustomEditor(Cor.class, new CorConverter(corDAO));
	}
	
	@RequestMapping(path="/novo")
	public ModelAndView init(){
		ModelAndView mv = new ModelAndView("produto/cadastro");
		Produto produto = new Produto();
		produto.setSituacao(true);
		mv.addObject("categorias", categoriaDAO.findAll());
		mv.addObject("caracteristicas", caracteristicaDAO.findAll());
		mv.addObject("cores", corDAO.findAll());
		mv.addObject("produto", produto);
		return mv;
	}
	
	@RequestMapping(path="/salvar")
	public ModelAndView salvar(@Validated Produto produto, Errors errors, 
			@RequestParam(required=false) MultipartFile arquivo,
			@RequestParam(required=false) List<MultipartFile> arquivos,
			HttpServletRequest request){
		if(errors.hasErrors() || arquivo.isEmpty() || arquivos.isEmpty()){			
			ModelAndView mv = new ModelAndView("produto/cadastro");
			mv.addObject("categorias", categoriaDAO.findAll());
			mv.addObject("caracteristicas", caracteristicaDAO.findAll());
			mv.addObject("cores", corDAO.findAll());
			return mv;
		}
		produto.setFotoPrincipal(fileSaver.writeSingle("img", arquivo));
		Produto produtoTemp;
		produtoTemp = produtoDAO.save(produto);
		fileSaver.writeMultiple("img", arquivos).forEach(retorno -> {
			fotosDoProduto = new FotosDoProduto();
			fotosDoProduto.setProduto(produtoTemp);
			fotosDoProduto.setDiretorio(retorno);		
			fotosDoProdutoDAO.save(fotosDoProduto);
		});
		ModelAndView mv = new ModelAndView("redirect:/produto/novo");
		return mv;
	}
	
}
