package br.com.fiap.dao;

import java.util.Calendar;
import java.util.List;

import br.com.fiap.entity.Corrida;
import br.com.fiap.entity.Motorista;

public interface CorridaDAO extends GenericDAO<Corrida, Integer> {
	//2. Buscar por todas as corridas por um intervalo de datas:
	List<Corrida> buscarPorDatas(Calendar inicio, Calendar fim);
	
	//4. Buscar por todas as corridas de um motorista:
	List<Corrida> buscarPorMotorista(Motorista motorista);
	
	//5. Contar a quantidade de corridas de um passageiro:
	long contarQuantidadePorPassageiro(int codPas);
	
	//6. Buscar por todas as corridas por parte do nome do passageiro:
	List<Corrida> buscarPorNomePassageiro(String nome);
	
	//8. Contar a quantidade de corridas de um motorista por um determinado período de datas:
	long contarQuantidadePorMotoristaPeriodo(long codMot, Calendar inicio, Calendar fim);
	
	//9. Buscar pelos 10 maiores valores de corrida realizados por um passageiro:
	List<Corrida> buscarMaiorValorPorPassageiro(int passageiro);
	
	//10. Buscar pelas corridas realizadas entre um passageiro e motorista específicos:
	List<Corrida> buscarPorPassageiroEMotorista(int codPas, long codMot);

}
