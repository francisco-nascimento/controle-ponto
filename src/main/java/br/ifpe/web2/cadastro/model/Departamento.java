package br.ifpe.web2.cadastro.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Departamento {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer codigo;
	@NotBlank(message="Nome do departamento deve ser preenchido")
	private String nome;
	@ManyToOne
	private Funcionario chefe;
	private String sigla;
	private Situacao situacao;
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Funcionario getChefe() {
		return chefe;
	}
	public void setChefe(Funcionario chefe) {
		this.chefe = chefe;
	}
	public Situacao getSituacao() {
		return situacao;
	}
	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

}
