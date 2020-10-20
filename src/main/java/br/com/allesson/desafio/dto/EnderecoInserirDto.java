package br.com.allesson.desafio.dto;

import javax.validation.constraints.NotEmpty;

public class EnderecoInserirDto {
	
	@NotEmpty(message = "Cidade é obrigatório.")
	private String cidade;
	
	@NotEmpty(message = "Estado é obrigatório.")
	private String estado;

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
