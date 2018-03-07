package br.com.colibrimoveis.colibri.appdater.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.colibrimoveis.colibri.appdater.DAO.AmbienteDAO;
import br.com.colibrimoveis.colibri.appdater.DAO.CategoriaDAO;
import br.com.colibrimoveis.colibri.appdater.converter.AmbienteConverter;
import br.com.colibrimoveis.colibri.appdater.model.Ambiente;
import br.com.colibrimoveis.colibri.appdater.model.Categoria;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {
	
	@Autowired
	private AmbienteDAO ambienteDAO;
	@Autowired
	private CategoriaDAO categoriaDAO;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception{
		binder.registerCustomEditor(Ambiente.class, new AmbienteConverter(ambienteDAO));
	}
	
	@RequestMapping(path="/novo")
	public ModelAndView init(){
		ModelAndView mv = new ModelAndView("categoria/cadastro");
		Categoria categoria = new Categoria();
		categoria.setSituacao(true);
		mv.addObject("ambientes", ambienteDAO.findAll());
		mv.addObject("categoria",categoria);
		mv.addObject("todasCategorias", carregarCategorias());
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView salvar(@Validated Categoria categoria, Errors errors, RedirectAttributes redirect){
		if(errors.hasErrors()){
			ModelAndView mv = new ModelAndView("categoria/cadastro");
			mv.addObject("ambientes", ambienteDAO.findAll());
			mv.addObject("todasCategorias", carregarCategorias());
			return mv;
		}
		ModelAndView mv = new ModelAndView("redirect:/categoria/novo");
		categoriaDAO.save(categoria);
		redirect.addFlashAttribute("mensagem", "Categoria salva com sucesso!");
		return mv;
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.POST)
	public ModelAndView editar(@PathVariable("id") Long id){
		ModelAndView mv = new ModelAndView("categoria/cadastro");
		Categoria categoria = categoriaDAO.findOne(id);
		mv.addObject(categoria);
		mv.addObject("todasCategorias", carregarCategorias());
		mv.addObject("ambientes", ambienteDAO.findAll());
		return mv;
	}
	
	//métodos internos
	private List<Categoria> carregarCategorias(){
		return categoriaDAO.findAll();
	}
	
}
