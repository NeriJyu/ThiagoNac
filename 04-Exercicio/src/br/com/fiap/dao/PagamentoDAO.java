package br.com.fiap.dao;

import br.com.fiap.entity.Pagamento;

public interface PagamentoDAO extends GenericDAO<Pagamento, Integer>{

	//7. Somar todos os valores de pagamentos realizados por um passageiro:
	double somarPagamentoPorPassageiro(int cod);
}
