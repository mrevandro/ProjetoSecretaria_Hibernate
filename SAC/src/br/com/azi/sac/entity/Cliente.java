package br.com.azi.sac.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Cliente {
	@Id
	@GeneratedValue
	@Column(name = "idCliente")
	private Long id;

	@Column(length = 50)
	private String nome;

	@Column(length = 11)
	private String celular;

	@Column(length = 30)
	private String ramoAtividade;

	@Column(length = 30)
	private String complemento;

	@Column(length = 5)
	private String numero;

	@Column(length = 10)
	private String telefone;

	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="idLogradouro")
	private Logradouro logradouro;

	@OneToMany
	@JoinColumn(name="idCliente", insertable=false, updatable=false)
	private List<Recado> recados;

	@ManyToMany
	@JoinTable( name = "Agendamento_Cliente",
				joinColumns			= @JoinColumn(name="idCliente", insertable=false, updatable=false),
				inverseJoinColumns	= @JoinColumn(name="idAgendamento", insertable=false, updatable=false))
	private List<Agendamento> agendamentos;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getRamoAtividade() {
		return ramoAtividade;
	}

	public void setRamoAtividade(String ramoAtividade) {
		this.ramoAtividade = ramoAtividade;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Logradouro getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	public List<Recado> getRecados() {
		return recados;
	}

	public void setRecados(List<Recado> recados) {
		this.recados = recados;
	}

	public List<Agendamento> getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(List<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
