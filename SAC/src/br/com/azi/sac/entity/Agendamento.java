package br.com.azi.sac.entity;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Agendamento {
	@Id
	@GeneratedValue
	@Column(name = "idAgendamento")
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Calendar data;
	
	private Time horario;
	
	@Column(length = 1000)
	private String observacao;

	@Enumerated(EnumType.STRING)
	private EnumAgendamentoTipo agendamentoTipo;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable( name = "Agendamento_Colaborador",
	joinColumns			= @JoinColumn(name="idAgendamento"),
	inverseJoinColumns	= @JoinColumn(name="idColaborador"))
	@OrderColumn(name="indice")
	private List<Colaborador> colaboradores;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable( name = "Agendamento_Cliente",
	joinColumns			= @JoinColumn(name="idAgendamento"),
	inverseJoinColumns	= @JoinColumn(name="idCliente"))
	@OrderColumn(name="indice")
	private List<Cliente> clientes;

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Time getHorario() {
		return horario;
	}

	public void setHorario(Time horario) {
		this.horario = horario;
	}

	public EnumAgendamentoTipo getAgendamentoTipo() {
		return agendamentoTipo;
	}

	public void setAgendamentoTipo(EnumAgendamentoTipo agendamentoTipo) {
		this.agendamentoTipo = agendamentoTipo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(List<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
