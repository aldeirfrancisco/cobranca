package com.aldeir.cobranca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aldeir.cobranca.modelo.Titulo;

                                            //o modelo e o tipo do id 
public interface Titulos extends JpaRepository<Titulo, Long>{

}
