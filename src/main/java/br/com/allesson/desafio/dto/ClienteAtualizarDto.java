package br.com.allesson.desafio.dto;

import javax.validation.constraints.NotEmpty;

public class ClienteAtualizarDto {
	
	@NotEmpty(message = "Nome é obrigatório.")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
