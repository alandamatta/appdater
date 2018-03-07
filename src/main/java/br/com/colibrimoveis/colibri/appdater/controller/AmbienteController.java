package br.com.colibrimoveis.colibri.appdater.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.colibrimoveis.colibri.appdater.DAO.AmbienteDAO;
import br.com.colibrimoveis.colibri.appdater.model.Ambiente;
import br.com.colibrimoveis.colibri.appdater.model.Categoria;

@Controller
@RequestMapping("/ambiente")
public class AmbienteController {
	
	@Autowired
	private AmbienteDAO ambienteDAO;
	
	@RequestMapping(path="/novo")
	public ModelAndView init(){
		ModelAndView mv = new ModelAndView("ambiente/cadastro");
		Ambiente ambiente = new Ambiente();
		ambiente.setSituacao(true);
		mv.addObject(new Categoria());
		mv.addObject(ambiente);
		mv.addObject("todosAmbientes", carregarAmbientes());
		return mv;
	}
	
	@RequestMapping(path="/salvar", method=RequestMethod.POST)
	public ModelAndView salvar(@Validated Ambiente ambiente, Errors errors, RedirectAttributes redirect){
		if(errors.hasErrors()){
			ModelAndView mv = new ModelAndView("ambiente/cadastro");
			mv.addObject("todosAmbientes", carregarAmbientes());
			return mv;
		}
		ModelAndView mv = new ModelAndView("redirect:/ambiente/novo");
		ambienteDAO.save(ambiente);
		redirect.addFlashAttribute("mensagem", "Ambiente salvo com sucesso!");
		return mv;
	}
	
	@RequestMapping(value="{id}")
	public ModelAndView editar(@PathVariable("id") Long id, HttpServletRequest request){
		if(request.getMethod().equals("POST")){
			ModelAndView mv = new ModelAndView("ambiente/cadastro");
			Ambiente ambiente = ambienteDAO.findOne(id);
			mv.addObject(ambiente);
			mv.addObject("todosAmbientes", carregarAmbientes());
			return mv;
		}
		return new ModelAndView("redirect:/ambiente/novo");
	}
	
	//métodos internos
	private List<Ambiente> carregarAmbientes(){
		return ambienteDAO.findAll();
	}
	
}
