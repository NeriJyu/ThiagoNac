package br.com.fiap.test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.fiap.dao.CorridaDAO;
import br.com.fiap.dao.MotoristaDAO;
import br.com.fiap.dao.PagamentoDAO;
import br.com.fiap.dao.VeiculoDAO;
import br.com.fiap.dao.impl.CorridaDAOImpl;
import br.com.fiap.dao.impl.MotoristaDAOImpl;
import br.com.fiap.dao.impl.PagamentoDAOImpl;
import br.com.fiap.dao.impl.VeiculoDAOImpl;
import br.com.fiap.entity.Corrida;
import br.com.fiap.entity.FormaPagamento;
import br.com.fiap.entity.Genero;
import br.com.fiap.entity.Motorista;
import br.com.fiap.entity.Pagamento;
import br.com.fiap.entity.Passageiro;
import br.com.fiap.entity.Veiculo;
import br.com.fiap.exception.CodigoInexistenteException;
import br.com.fiap.exception.CommitException;
import br.com.fiap.singleton.EntityManagerFactorySingleton;

class PesquisasTest {

	private static MotoristaDAO motoristaDao;
	private static CorridaDAO corridaDao;
	private static VeiculoDAO veiculoDao;
	private static PagamentoDAO pagamentoDao;
	
	@BeforeAll
	public static void init() {
		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		motoristaDao = new MotoristaDAOImpl(em);
		corridaDao = new CorridaDAOImpl(em);
		veiculoDao = new VeiculoDAOImpl(em);
		pagamentoDao = new PagamentoDAOImpl(em);
		
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
			corridaDao.cadastrar(corrida);
			corridaDao.commit();
		} catch (CommitException e) {
			e.printStackTrace();
			fail("Falha no teste");
		}
	}
	
	@Test
	@DisplayName("Exercício 01 - Pesquisar motorista por parte do nome")
	void exercicio01Test() {
				
		List<Motorista> lista = motoristaDao.buscarPorNome("a");
		
		assertNotEquals(0, lista.size());
		
		for (Motorista motorista : lista) {
			assertTrue(motorista.getNome().contains("a"));
		}
	}

	@Test
	@DisplayName("Exercicio 02 - Buscar corridas por intervalo de datas")
	void exercicio02Test() {
		
		Calendar inicio = new GregorianCalendar(2018,Calendar.DECEMBER,1);
		Calendar fim = new GregorianCalendar(2019,Calendar.DECEMBER,1);
		
		List<Corrida> corridas = corridaDao.buscarPorDatas(inicio, fim);
		
		assertNotEquals(0, corridas.size());
		
		for (Corrida corrida : corridas) {
			assertTrue(corrida.getData().after(inicio) && corrida.getData().before(fim));
		}
		
	}
	
	@Test
	@DisplayName("Exercício 03 - Buscar veículos por valor mínimo de ano")
	void exercicio03Test() {
		List<Veiculo> veiculos = veiculoDao.buscarPorAnoMinimo(2015);
		assertNotEquals(0, veiculos.size());
		
		for (Veiculo veiculo : veiculos) {
			assertTrue(veiculo.getAno() <= 2015 );
		}
	}
	
	@Test
	@DisplayName("Exercício 04 - Buscar corridas de um motorista específico")
	void exercicio04Test() {
		Motorista motorista;
		try {
			motorista = motoristaDao.buscar(132l);
			List<Corrida> corridas = corridaDao.buscarPorMotorista(motorista);
			
			assertNotEquals(0, corridas.size());
			
			for (Corrida corrida : corridas) {
				assertEquals(corrida.getMotorista().getNumeroCarteira(), motorista.getNumeroCarteira());
			}
			
		} catch (CodigoInexistenteException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Exercício 05 - Contar a quantidade de corridas de um passageiro")
	void exercicio05Test() {
		long qtd = corridaDao.contarQuantidadePorPassageiro(1);
		assertEquals(1, qtd);
	}
	
	@Test
	@DisplayName("Exercício 06 - Buscar por todas as corridas por parte do nome do passageiro")
	void exercicio06Test() {
		List<Corrida> corridas = corridaDao.buscarPorNomePassageiro("Ma");
		assertNotEquals(0, corridas.size());
		for (Corrida corrida : corridas) {
			assertTrue(corrida.getPassageiro().getNome().contains("Ma"));
		}
	}
	
	@Test
	@DisplayName("Exercicio 07 - Somar todos os valores de pagamentos realizados por um passageiro")
	void exercicio07Test() {
		double total = pagamentoDao.somarPagamentoPorPassageiro(1);
		assertEquals(100, total);
	}

	@Test
	@DisplayName("Exercicio 08 - Contar a quantidade de corridas de um motorista por um determinado período de datas")
	void exercicio08Test() {
		Calendar inicio = new GregorianCalendar(2018,Calendar.DECEMBER,1);
		Calendar fim = new GregorianCalendar(2019,Calendar.DECEMBER,1);
		
		long qtd = corridaDao.contarQuantidadePorMotoristaPeriodo(132, inicio, fim);
		
		assertEquals(1, qtd);
	}

	@Test
	@DisplayName("Exercicio 09 - Buscar pelos 10 maiores valores de corrida realizados por um passageiro")
	void exercicio09Test() {
		List<Corrida> lista = corridaDao.buscarMaiorValorPorPassageiro(1);
		assertNotEquals(0, lista.size());
	}
	
	@Test
	@DisplayName("Exercicio 10 - Buscar pelas corridas realizadas entre um passageiro e motorista específicos")
	void exercicio10Test() {
		List<Corrida> lista = corridaDao.buscarPorPassageiroEMotorista(1, 132);
		assertNotEquals(0, lista.size());
		for (Corrida corrida : lista) {
			assertEquals(1, corrida.getPassageiro().getCodigo());
			assertEquals(132, corrida.getMotorista().getNumeroCarteira());
		}
	}
	
	
}







