package br.com.fiap.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.fiap.dao.VeiculoDAO;
import br.com.fiap.entity.Veiculo;

public class VeiculoDAOImpl extends GenericDAOImpl<Veiculo, Integer> implements VeiculoDAO {

	public VeiculoDAOImpl(EntityManager em) {
		super(em);
	}
	@Override
    public List<Veiculo> buscarPorAnoMinimo(int ano) {
       
        TypedQuery<Veiculo> query = em.createQuery("from Veiculo v where v.ano < :n", Veiculo.class);   
        query.setParameter("n", ano);
        return query.getResultList();
       
    }
}