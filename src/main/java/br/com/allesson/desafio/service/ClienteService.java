package br.com.allesson.desafio.service;

import java.util.List;

import org.springframework.data.domain.Page;

import br.com.allesson.desafio.dto.ClienteAtualizarDto;
import br.com.allesson.desafio.dto.ClienteDto;
import br.com.allesson.desafio.dto.ClienteInserirDto;

public interface ClienteService {
	
	public ClienteDto inserirCliente(ClienteInserirDto cliente);
	
	public Page<ClienteDto> listarClientes(int pagina);
	
	public ClienteDto filtrarId(Long id);
	
	public List<ClienteDto> filtrarNome(String nome);
	
	public ClienteDto atualizarNomeCliente(Long id, ClienteAtualizarDto nome);
	
	public void deletarCliente(Long id);

}
