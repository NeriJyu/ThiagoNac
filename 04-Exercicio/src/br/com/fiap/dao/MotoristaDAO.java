package br.com.fiap.dao;

import java.util.List;

import br.com.fiap.entity.Motorista;

public interface MotoristaDAO extends GenericDAO<Motorista, Long>{
	
	//1. Buscar os motoristas por parte do nome
	List<Motorista> buscarPorNome(String nome);
	
	
}



