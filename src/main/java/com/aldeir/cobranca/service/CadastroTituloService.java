package com.aldeir.cobranca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.aldeir.cobranca.modelo.Titulo;
import com.aldeir.cobranca.repository.Titulos;

@Service
public class CadastroTituloService {

	@Autowired
	private Titulos titulos;
	
	public void salvar(Titulo titulo) {
		try {
		titulos.save(titulo);
		}catch(DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato data invalido!");
		}
	}

	public void excluir(Long codigo) {
		titulos.deleteById(codigo);
		
	}
}
