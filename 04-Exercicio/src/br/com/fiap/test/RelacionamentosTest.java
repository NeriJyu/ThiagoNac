package br.com.fiap.test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import br.com.fiap.dao.CorridaDAO;
import br.com.fiap.dao.impl.CorridaDAOImpl;
import br.com.fiap.entity.Corrida;
import br.com.fiap.entity.FormaPagamento;
import br.com.fiap.entity.Genero;
import br.com.fiap.entity.Motorista;
import br.com.fiap.entity.Pagamento;
import br.com.fiap.entity.Passageiro;
import br.com.fiap.entity.Veiculo;
import br.com.fiap.exception.CommitException;
import br.com.fiap.singleton.EntityManagerFactorySingleton;

class RelacionamentosTest {

	private static CorridaDAO dao;
	
	@BeforeAll
	public static void init() {
		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		dao = new CorridaDAOImpl(em);
	}
	
	@Test
	void test() {
		Veiculo veiculo1 = new Veiculo("ABC-1234", "Branco", 1990);
		Veiculo veiculo2 = new Veiculo("AAA-5454", "Preto", 2010);
		
		List<Veiculo> veiculos = new ArrayList<>();
		veiculos.add(veiculo1);
		veiculos.add(veiculo2);
		
		Motorista motorista = new Motorista(132,"Thiago", Calendar.getInstance(), null, Genero.MASCULINO, veiculos);
		
		Pagamento pagamento = new Pagamento(Calendar.getInstance(), 100, FormaPagamento.CREDITO);
		
		Passageiro passageiro = new Passageiro("Marcus", Calendar.getInstance(), Genero.MASCULINO);
		
		Corrida corrida = new Corrida("FIAP","Paulista", Calendar.getInstance(), 100, motorista, passageiro, pagamento);
		
		pagamento.setCorrida(corrida);
		
		try {
			dao.cadastrar(corrida);
			dao.commit();
		} catch (CommitException e) {
			e.printStackTrace();
			fail("Falha no teste");
		}
		
		/**
		 
	 	SELECT * FROM T_MOTORISTA;
		SELECT * FROM T_PASSAGEIRO;
		SELECT * FROM T_PAGAMENTO;
		SELECT * FROM T_CORRIDA;
		SELECT * FROM T_VEICULO;
		SELECT * FROM T_VEICULO_MOTORISTA;
		 
		 */
		
	}

}
