package com.aldeir.cobranca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aldeir.cobranca.modelo.Titulo;
import com.aldeir.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	  @Autowired  //injeta a implementação do titulo
	 private Titulos titulos;
	 
	@RequestMapping("/novo")
	public String novo() {
		return "CadastroTitulo.xhtml";
	}
	
	@RequestMapping(method = RequestMethod.POST)//aqui 
	public String salvar(Titulo titulo) {//estar vindo via post e converte em obgeto para ser salvo no banco
	
		titulos.save(titulo);
		return "CadastroTitulo.xhtml";
	}

}
