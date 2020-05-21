package br.com.fiap.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_PAGAMENTO")
@SequenceGenerator(name = "pagamento", sequenceName = "SQ_T_PAGAMENTO", allocationSize = 1)
public class Pagamento {

	@Id
	@Column(name = "cd_pagamento")
	@GeneratedValue(generator = "pagamento", strategy = GenerationType.SEQUENCE)
	private int codigo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_pagamento", nullable = false)
	private Calendar data;

	@Column(name = "vl_pagamento", nullable = false)
	private float valor;

	@Enumerated(EnumType.STRING)
	@Column(name = "ds_forma_pagamento", nullable = false)
	private FormaPagamento forma;
	
	@OneToOne
	@JoinColumn(name="cd_corrida")
	private Corrida corrida;

	public Pagamento() {
		super();
	}

	public Pagamento(Calendar data, float valor, FormaPagamento forma) {
		super();
		this.data = data;
		this.valor = valor;
		this.forma = forma;
	}

	public Pagamento(int codigo, Calendar data, float valor, FormaPagamento forma) {
		super();
		this.codigo = codigo;
		this.data = data;
		this.valor = valor;
		this.forma = forma;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public FormaPagamento getForma() {
		return forma;
	}

	public void setForma(FormaPagamento forma) {
		this.forma = forma;
	}

	public Corrida getCorrida() {
		return corrida;
	}

	public void setCorrida(Corrida corrida) {
		this.corrida = corrida;
	}

}
