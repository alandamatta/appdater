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

import br.com.colibrimoveis.colibri.appdater.DAO.CaracteristicaDAO;
import br.com.colibrimoveis.colibri.appdater.model.Caracteristica;

@Controller
@RequestMapping(path="/caracteristica")
public class CaracteristicaController {
	
	@Autowired
	private CaracteristicaDAO caracteristicaDAO;
	
	@RequestMapping(path="/novo")
	public ModelAndView init(){
		ModelAndView mv = new ModelAndView("caracteristica/cadastro");
		Caracteristica caracteristica = new Caracteristica();
		caracteristica.setSituacao(true);
		mv.addObject("todasCaracteristicas", carregarCaracteristicas());
		mv.addObject("caracteristica", caracteristica);
		return mv;
	}
	
	@RequestMapping(path="/salvar", method=RequestMethod.POST)
	public ModelAndView salvar(@Validated Caracteristica caracteristica, Errors errors, RedirectAttributes redirect){
		if(errors.hasErrors()){
			ModelAndView mv = new ModelAndView("caracteristica/cadastro");
			mv.addObject("todasCaracteristicas", carregarCaracteristicas());
			return mv;
		}
		ModelAndView mv = new ModelAndView("redirect:/caracteristica/novo");
		caracteristicaDAO.save(caracteristica);
		redirect.addFlashAttribute("mensagem", "Característica salva com sucesso!");
		return mv;
	}
	
	@RequestMapping(value="{id}")
	public ModelAndView editar(@PathVariable Long id, HttpServletRequest request){
		if(request.getMethod().equals("POST")){
			ModelAndView mv = new ModelAndView("caracteristica/cadastro");
			mv.addObject("caracteristica", caracteristicaDAO.findOne(id));
			mv.addObject("caracteristicas", carregarCaracteristicas());
			return mv;
		}
		return new ModelAndView("redirect:/caracteristica/novo");
	}
	
	//métodos internos
	private List<Caracteristica> carregarCaracteristicas(){
		return caracteristicaDAO.findAll();
	}
	
}
