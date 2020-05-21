package br.com.fiap.test;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.fiap.dao.VeiculoDAO;
import br.com.fiap.dao.impl.VeiculoDAOImpl;
import br.com.fiap.entity.Veiculo;
import br.com.fiap.exception.CodigoInexistenteException;
import br.com.fiap.exception.CommitException;
import br.com.fiap.singleton.EntityManagerFactorySingleton;

class GenericDAOTest {

	private static VeiculoDAO dao;

	private Veiculo veiculo;

	@BeforeAll
	public static void init() {
		EntityManagerFactory fabrica = EntityManagerFactorySingleton.getInstance();
		EntityManager em = fabrica.createEntityManager();
		dao = new VeiculoDAOImpl(em);
	}

	@BeforeEach 
	public void cadastro() {
		veiculo = new Veiculo("ABC-1234", "Preto", 2010);

		try {
			dao.cadastrar(veiculo);
			dao.commit();
		} catch (CommitException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	@DisplayName("Teste de remoção com sucesso")
	public void remover() {

		try {
			dao.remover(veiculo.getCodigo());
			dao.commit();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		assertThrows(CodigoInexistenteException.class, ()-> dao.buscar(veiculo.getCodigo()));
	}

	@Test
	@DisplayName("Teste de atualização com sucesso")
	public void atualizar() {

		Veiculo atualizacao = new Veiculo(veiculo.getCodigo(), "AAA-0000", "Branco", 1990);

		try {
			dao.atualizar(atualizacao);
			dao.commit();
		} catch (CommitException e) {
			e.printStackTrace();
			fail();
		}

		Veiculo busca;
		try {
			busca = dao.buscar(atualizacao.getCodigo());
			assertEquals("AAA-0000", busca.getPlaca());
			assertEquals(1990, busca.getAno());
		} catch (CodigoInexistenteException e) {
			e.printStackTrace();
			fail();
		}
		
		
	}

	@Test
	@DisplayName("Teste de busca com sucesso")
	public void buscar() {

		try {
			Veiculo busca = dao.buscar(veiculo.getCodigo());
			
			assertNotNull(busca);
			assertEquals("ABC-1234", busca.getPlaca());

		} catch (CodigoInexistenteException e) {
			e.printStackTrace();
			fail();
		}
	
	}

	@Test
	@DisplayName("Teste de cadastro com sucesso")
	public void cadastrar() {
		assertNotEquals(0, veiculo.getCodigo());
	}
}
