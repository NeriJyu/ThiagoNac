package br.com.fiap.dao.impl;

import javax.persistence.EntityManager;

import br.com.fiap.dao.PagamentoDAO;
import br.com.fiap.entity.Pagamento;

public class PagamentoDAOImpl extends GenericDAOImpl<Pagamento, Integer> implements PagamentoDAO{

	public PagamentoDAOImpl(EntityManager em) {
		super(em);
	}
	//7. Somar todos os valores de pagamentos realizados por um passageiro:
	@Override
    public double somarPagamentoPorPassageiro(int cod) {
       
        return em.createQuery("select sum(p.valor)from Pagamento p where p.corrida.passageiro.id = :t", Double.class)
                .setParameter("t", cod)
                .getSingleResult();   
    }
}
