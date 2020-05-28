package com.aldeir.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aldeir.cobranca.modelo.StatusTitulo;
import com.aldeir.cobranca.modelo.Titulo;
import com.aldeir.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {

	@Autowired // injeta a implementação do titulos da interfaces
	private Titulos titulos;

	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("CadastroTitulo.xhtml");
		// mv.addObject("status", StatusTitulo.values()); pode ser assim para mostra o
		// no th:each na tag select ou na linha 40
		mv.addObject(new Titulo());
		return mv;
	}

	@RequestMapping
	public ModelAndView pesquisar() {
		List<Titulo> todosTitulos = titulos.findAll();// faz uma busca em todos os titulos no banco
		ModelAndView mv = new ModelAndView("PesquisaTitulo.xhtml");
		mv.addObject("titulos", todosTitulos);// retorna um objeto para a viw
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST) // aqui
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {// estar vindo via
																									// post e converte
																									// em obgeto para
																									// ser salvo no
																									// banco
		// aqui encima pede para validar antes de enviar para o banco

		if (errors.hasErrors()) {
			return "CadastroTitulo.xhtml";// si tiver algum erro retorna o nome na viu
		}

		titulos.save(titulo);
		attributes.addFlashAttribute("mensagem", "Titulo salvo com sucesso!");

		return "redirect:/titulos/novo";// retorn uma url 
	}

	// atributo para ser usado no th:each na tag select
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStatusTitulo() {
		return Arrays.asList(StatusTitulo.values());
	}

}
