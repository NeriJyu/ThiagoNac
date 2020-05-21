package br.com.fiap.dao.impl;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;

import br.com.fiap.dao.GenericDAO;
import br.com.fiap.exception.CodigoInexistenteException;
import br.com.fiap.exception.CommitException;

public abstract class GenericDAOImpl<T,K> implements GenericDAO<T, K>{

	protected EntityManager em;
	
	private Class<T> clazz;
	
	@SuppressWarnings("all")
	public GenericDAOImpl(EntityManager em) {
		this.em = em;
		clazz = (Class<T>) ((ParameterizedType) getClass()
			.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public void cadastrar(T tabela) {
		em.persist(tabela);
	}

	public void remover(K codigo) throws CodigoInexistenteException {
		em.remove(buscar(codigo));
	}

	public void atualizar(T tabela) {
		em.merge(tabela);
	}

	public T buscar(K codigo) throws CodigoInexistenteException {
		T tabela = em.find(clazz, codigo); 
		if (tabela == null) {
			throw new CodigoInexistenteException();
		}
		return tabela;
	}

	public void commit() throws CommitException {
		try {
			em.getTransaction().begin();
			em.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			throw new CommitException();
		}
	}

}