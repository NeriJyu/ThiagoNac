package br.com.fiap.dao;

import java.util.List;

import br.com.fiap.entity.Veiculo;

public interface VeiculoDAO extends GenericDAO<Veiculo, Integer>{
	List<Veiculo> buscarPorAnoMinimo(int ano);
}



