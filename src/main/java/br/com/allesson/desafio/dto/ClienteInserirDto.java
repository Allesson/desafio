package br.com.allesson.desafio.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ClienteInserirDto {
	
	@NotEmpty(message = "Nome é obrigatório.")
	private String nome;
	
	@NotEmpty(message = "Sexo é obrigatório.")
	private String sexo;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataNasc;
	
	private int idade;
	
	@NotNull(message = "Endereço  é obrigatório.")
	@Min(value = 1, message = "Endereço  é obrigatório.")
	private Long idEndereco;

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

	public Long getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Long idEndereco) {
		this.idEndereco = idEndereco;
	}
	
	
}
