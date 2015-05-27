package br.com.azi.sac.entity;

import java.sql.Time;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Colaborador {
	@Id
	@GeneratedValue
	@Column(name = "idColaborador")
	private Long id;

	@Column(length = 50)
	private String nome;
	private Integer ramal;
	private Boolean ativo;

	@Column(length = 11)
	private String celular;
	
	private Time horaInicioIntervalo;
	
	private Time horaFinalIntervalo;
	
	private Time horaEntrada;
	
	private Time horaSaida;

	@Column(length = 10)
	private String telefone;
	
	@ManyToMany
	@JoinTable(name = "Agendamento_Colaborador",
		joinColumns			= @JoinColumn(name="idColaborador", insertable=false, updatable=false),
		inverseJoinColumns	= @JoinColumn(name="idAgendamento", insertable=false, updatable=false)
	)
	private List<Agendamento> agendamentos;
	
	@OneToMany
	@JoinColumn(name="idColaborador", insertable=false, updatable=false)
	private List<Recado> recados;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getRamal() {
		return ramal;
	}

	public void setRamal(Integer ramal) {
		this.ramal = ramal;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Time getHoraFinalIntervalo() {
		return horaFinalIntervalo;
	}

	public void setHoraFinalIntervalo(Time horaFinalIntervalo) {
		this.horaFinalIntervalo = horaFinalIntervalo;
	}

	public Time getHoraInicioIntervalo() {
		return horaInicioIntervalo;
	}

	public void setHoraInicioIntervalo(Time horaInicioIntervalo) {
		this.horaInicioIntervalo = horaInicioIntervalo;
	}

	public Time getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(Time horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public Time getHoraSaida() {
		return horaSaida;
	}

	public void setHoraSaida(Time horaSaida) {
		this.horaSaida = horaSaida;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Agendamento> getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(List<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}

}
