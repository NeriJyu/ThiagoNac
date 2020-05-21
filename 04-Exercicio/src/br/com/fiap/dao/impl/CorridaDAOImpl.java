package br.com.fiap.dao.impl;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.fiap.dao.CorridaDAO;
import br.com.fiap.entity.Corrida;
import br.com.fiap.entity.Motorista;

public class CorridaDAOImpl extends GenericDAOImpl<Corrida, Integer> implements CorridaDAO{

	public CorridaDAOImpl(EntityManager em) {
		super(em);
	}
	
	//2. Buscar por todas as corridas por um intervalo de datas:
	@Override
	public List<Corrida> buscarPorDatas(Calendar inicio, Calendar fim) {
		return em.createQuery("from Corrida c where c.data between :i and :f", Corrida.class)
				.setParameter("i", inicio)
				.setParameter("f", fim)
				.setMaxResults(30)
				.getResultList();
	}
	
	//4. Buscar por todas as corridas de um motorista:
	@Override
    public List<Corrida> buscarPorMotorista(Motorista motorista) {
       
        TypedQuery<Corrida> query = em.createQuery("from Corrida c where c.motorista.numeroCarteira = :n ", Corrida.class);
        query.setParameter("n", motorista.getNumeroCarteira());
       
        return query.getResultList();
    }

	//5. Contar a quantidade de corridas de um passageiro:
	@Override
	public long contarQuantidadePorPassageiro(int codPas) {
		return em.createQuery("select count(c) from Corrida c where c.passageiro.codigo = :e",
				Long.class)
				.setParameter("e", codPas)
				.getSingleResult(); 
	}
	
	//6. Buscar por todas as corridas por parte do nome do passageiro:
	@Override
    public List<Corrida> buscarPorNomePassageiro(String nome) {
       
        TypedQuery<Corrida> query = em.createQuery("from Corrida c where c.passageiro.nome like :n ", Corrida.class);
        query.setParameter("n", "%" + nome + "%");
        query.setMaxResults(40);
       
        return query.getResultList();
    }
	
	//8. Contar a quantidade de corridas de um motorista por um determinado período de datas:
	@Override
	public long contarQuantidadePorMotoristaPeriodo(long codMot, Calendar inicio, Calendar fim) {
		return em.createNativeQuery("select count(c) from Corrida c where c.motorista.numeroCarteira = :u and c.data between :i and :f", Long.class)
				.setParameter("u", codMot)
				.setParameter("i", inicio)
				.setParameter("f", fim)
				.getFirstResult();
	}
//	em.createQuery("select count(c) from Corrida c where c.motorista.numeroCarteira = :u and c.data between :i and :f",
//			Long.class)
//			.setParameter("u", codMot)
//			.setParameter("i", inicio)
//			.setParameter("f", fim)
//			.getSingleResult()
	
	//9. Buscar pelos 10 maiores valores de corrida realizados por um passageiro:
	@Override
    public List<Corrida> buscarMaiorValorPorPassageiro(int passageiro) {
       
        return em.createQuery("from Corrida c where c.passageiro.codigo = :n order by c.valor", Corrida.class)
                .setParameter("n", passageiro)
                .setMaxResults(10)
                .getResultList();
       
    }
	
	//10. Buscar pelas corridas realizadas entre um passageiro e motorista específicos:
	@Override
	public List<Corrida> buscarPorPassageiroEMotorista(int codPas, long codMot) {
		return em.createQuery("from Corrida c where c.passageiro.codigo = :p and c.motorista.numeroCarteira = :m", Corrida.class)
				.setParameter("p", codPas)
				.setParameter("m", codMot)
				.getResultList();
	}
	
}
