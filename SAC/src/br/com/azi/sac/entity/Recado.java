package br.com.azi.sac.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Recado {
	@Id
	@GeneratedValue
	@Column(name = "idRecado")
	private Long id;

	@Column(length = 1000)
	private String conteudo;
	
	@Temporal(TemporalType.DATE)
	private Calendar data;
	private Boolean urgente;
	
	@ManyToOne
	@JoinColumn(name="idCliente")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="idColaborador")
	private Colaborador colaborador;

	public Boolean getUrgente() {
		return urgente;
	}

	public void setUrgente(Boolean urgente) {
		this.urgente = urgente;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
