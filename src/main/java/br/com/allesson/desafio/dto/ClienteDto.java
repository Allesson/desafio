package br.com.allesson.desafio.dto;

import java.time.LocalDate;

import br.com.allesson.desafio.entity.Endereco;

public class ClienteDto {
	
	private Long id;
	private String nome;
	private String sexo;
	private LocalDate dataNasc;
	private int idade;
	private Endereco endereco;
	
	public ClienteDto() {
		
	}
	
	public ClienteDto(String nome, String sexo, LocalDate dataNasc, int idade, Endereco endereco) {
		this.nome = nome;
		this.sexo = sexo;
		this.dataNasc = dataNasc;
		this.idade = idade;
		this.endereco = endereco;
	}
	
	public ClienteDto(Long id, String nome, String sexo, LocalDate dataNasc, int idade, Endereco endereco) {
		this.id = id;
		this.nome = nome;
		this.sexo = sexo;
		this.dataNasc = dataNasc;
		this.idade = idade;
		this.endereco = endereco;
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

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public LocalDate getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(LocalDate dataNasc) {
		this.dataNasc = dataNasc;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

}
