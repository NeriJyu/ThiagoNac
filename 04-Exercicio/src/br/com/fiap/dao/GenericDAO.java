package br.com.fiap.dao;

import br.com.fiap.exception.CodigoInexistenteException;
import br.com.fiap.exception.CommitException;

public interface GenericDAO<T, K> {

	void cadastrar(T tabela);

	void remover(K codigo) throws CodigoInexistenteException;

	void atualizar(T tabela);

	T buscar(K codigo) throws CodigoInexistenteException;

	void commit() throws CommitException;

}