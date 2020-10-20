package br.com.allesson.desafio.service;

import java.util.List;

import org.springframework.data.domain.Page;

import br.com.allesson.desafio.dto.EnderecoDto;
import br.com.allesson.desafio.dto.EnderecoInserirDto;

public interface EnderecoService {
	
	public EnderecoDto inserirEndereco(EnderecoInserirDto endereco);
	
	public Page<EnderecoDto> listarEnderecos(int pagina);
	
	public List<EnderecoDto> filtrarCidade(String cidade);
	
	public List<EnderecoDto> filtrarEstado(String estado);

}
