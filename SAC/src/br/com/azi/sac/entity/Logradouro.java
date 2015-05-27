package br.com.azi.sac.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Logradouro {
	@Id
	@GeneratedValue
	@Column(name = "idLogradouro")
	private Long id;
	
	@Column(length=100)
	private String nome;
	private Integer cep;
	private Integer numFinal;
	private Integer numInicial;

	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="idBairro")
	private Bairro bairro;

	@Enumerated(EnumType.STRING)
	private EnumLogradouroTipo logradouroTipo;

	public Integer getCep() {
		return cep;
	}

	public void setCep(Integer cep) {
		this.cep = cep;
	}

	public Integer getNumFinal() {
		return numFinal;
	}

	public void setNumFinal(Integer numFinal) {
		this.numFinal = numFinal;
	}

	public Integer getNumInicial() {
		return numInicial;
	}

	public void setNumInicial(Integer numInicial) {
		this.numInicial = numInicial;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public EnumLogradouroTipo getLogradouroTipo() {
		return logradouroTipo;
	}

	public void setLogradouroTipo(EnumLogradouroTipo logradouroTipo) {
		this.logradouroTipo = logradouroTipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
