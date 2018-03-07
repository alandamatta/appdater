package br.com.colibrimoveis.colibri.appdater.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.colibrimoveis.colibri.appdater.DAO.CorDAO;
import br.com.colibrimoveis.colibri.appdater.model.Cor;

@Controller
@RequestMapping("/cor")
public class CorController {
	
	@Autowired
	private CorDAO corDAO;
	
	@RequestMapping(path="/novo")
	public ModelAndView init(){
		ModelAndView mv = new ModelAndView("cor/cadastro");
		mv.addObject("todasCores", carregarCores());
		Cor cor = new Cor();
		cor.setSituacao(true);
		mv.addObject("cor", cor);
		return mv;
	}
	
	@RequestMapping(path="/salvar" ,method=RequestMethod.POST)
	public ModelAndView salvar(@Validated Cor cor, Errors errors, RedirectAttributes redirect){
		if(errors.hasErrors()){
			ModelAndView mv = new ModelAndView("cor/cadastro");
			mv.addObject("todasCores", carregarCores());
			return mv;
		}
		ModelAndView mv = new ModelAndView("redirect:/cor/novo");
		corDAO.save(cor);
		redirect.addFlashAttribute("mensagem", "Cor salva com sucesso!");
		return mv;
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.POST)
	public ModelAndView editar(@PathVariable("id") Long id){
		ModelAndView mv = new ModelAndView("cor/cadastro");
		mv.addObject("todasCores", carregarCores());
		mv.addObject("cor", corDAO.findOne(id));
		return mv;
	}
	
	
	//métodos internos
	private List<Cor> carregarCores(){
		return corDAO.findAll();
	}
	
}
