package com.aldeir.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.aldeir.cobranca.modelo.StatusTitulo;
import com.aldeir.cobranca.modelo.Titulo;
import com.aldeir.cobranca.repository.Titulos;
import com.aldeir.cobranca.repository.filter.TituloFilter;

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

	public String receber(Long codigo) {
		 Titulo titulo = titulos.getOne(codigo);
		 titulo.setStatus(StatusTitulo.RECEBIDO);
	     titulos.save(titulo);
	     return StatusTitulo.RECEBIDO.getDescricao();
	}
	public List<Titulo> filtrar(TituloFilter filtro){
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		return  titulos.findByDescricaoContaining(descricao);// faz uma busca em todos os titulos no banco
	
	}
}
