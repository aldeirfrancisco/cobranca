package com.aldeir.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aldeir.cobranca.modelo.StatusTitulo;
import com.aldeir.cobranca.modelo.Titulo;
import com.aldeir.cobranca.repository.Titulos;
import com.aldeir.cobranca.repository.filter.TituloFilter;
import com.aldeir.cobranca.service.CadastroTituloService;

@Controller
@RequestMapping("/titulos")
public class TituloController {

    
	@Autowired
    private CadastroTituloService  cadastroTituloService;
	
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("CadastroTitulo.html");
		// mv.addObject("status", StatusTitulo.values()); pode ser assim para mostra o
		// no th:each na tag select ou na linha 40
		mv.addObject(new Titulo());
		return mv;
	}

	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") TituloFilter filtro) {
			
		List<Titulo> todosTitulos = cadastroTituloService.filtrar(filtro); // faz uma busca em todos os titulos no banco
	
		ModelAndView mv = new ModelAndView("PesquisaTitulo.html");
		mv.addObject("titulos", todosTitulos);// retorna um objeto para a viw
		return mv;
	}
	
	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Titulo titulo) {
		//Titulo titulo = titulos.getOne(codigo);pode ser assim com o parametro como id ou pode ser com o paramentro do tipo do objeto 
		
		ModelAndView mv = new ModelAndView("CadastroTitulo.html");
		mv.addObject(titulo);
		return mv;
    }
	@RequestMapping(value="{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		
		 cadastroTituloService.excluir(codigo);
		 
		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");
		return "redirect:/titulos";
	}
	
	
	@RequestMapping(method = RequestMethod.POST) // aqui
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {// estar vindo via post e converte em obgeto para ser salvo no banco 																				// banco
		// aqui encima pede para validar antes de enviar para o banco
		if (errors.hasErrors()) {
						return "CadastroTitulo.html";// si tiver algum erro retorna o nome na viu
					}
			  try {
				  cadastroTituloService.salvar(titulo);
					attributes.addFlashAttribute("mensagem", "Titulo salvo com sucesso!");
			
					return "redirect:/titulos/novo";// retorn uma url 
			  }catch (DataIntegrityViolationException e) {
				errors.rejectValue("dataVencimento", null,e.getMessage());
				return "CadastroTitulo.html";
			}
	}
   
	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo) {
		return cadastroTituloService.receber(codigo);
		
	}

	// atributo para ser usado no th:each na tag select
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStatusTitulo() {
		return Arrays.asList(StatusTitulo.values());
	}

}
