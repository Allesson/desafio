package br.com.allesson.desafio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.allesson.desafio.dto.ClienteAtualizarDto;
import br.com.allesson.desafio.dto.ClienteDto;
import br.com.allesson.desafio.dto.ClienteInserirDto;
import br.com.allesson.desafio.entity.Cliente;
import br.com.allesson.desafio.entity.Endereco;
import br.com.allesson.desafio.repository.ClienteRepository;
import br.com.allesson.desafio.repository.EnderecoRepository;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	private static final int TAMANHO_PAGINA = 10;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	public ClienteDto inserirCliente(ClienteInserirDto cliente) {
		validarNome(cliente.getNome());
		
		Optional<Endereco> endereco = enderecoRepository.findById(cliente.getIdEndereco());
		endereco.orElseThrow(() -> new PersistenceException("Endereço informado não encontrado."));
		
		Endereco enderecopersisted = endereco.get();
		ClienteDto clienteDto = new ClienteDto(cliente.getNome(), cliente.getSexo(), cliente.getDataNasc(),
				cliente.getIdade(), enderecopersisted);
		
		return modelMapper.map(clienteRepository.save(modelMapper.map(clienteDto, Cliente.class)),
				ClienteDto.class);
	}
	
	public Page<ClienteDto> listarClientes(int pagina) {
		Page<ClienteDto> retornoClientes = findAll(pagina);
		return retornoClientes;
	}
	
	public ClienteDto filtrarId(Long id) {
		Optional<Cliente> retornoCliente = validarId(id);
		return modelMapper.map(retornoCliente.get(), ClienteDto.class);
	}
	
	public List<ClienteDto> filtrarNome(String nome) {
		List<ClienteDto> clienteDto = findByNomeLike(nome);
		if (clienteDto == null || clienteDto.isEmpty()) {
			throw new PersistenceException("Nome informado não encontrado.");
		}
		return clienteDto;
	}
	
	public ClienteDto atualizarNomeCliente(Long id, ClienteAtualizarDto nome) {
		validarId(id).get();
		validarNome(nome.getNome());
		
		Cliente entity = clienteRepository.getOne(id);
		entity.setNome(nome.getNome());
		return modelMapper.map(clienteRepository.save(modelMapper.map(entity, Cliente.class)),
				ClienteDto.class);
	}
	
	public void deletarCliente(Long id) {
		validarId(id);
		clienteRepository.deleteById(id);
	}
	
	private Page<ClienteDto> findAll(int pagina) {
		Page<ClienteDto> clienteDto;
		PageRequest pageable = PageRequest.of(pagina, TAMANHO_PAGINA, Sort.Direction.ASC,
                "id");
		Page<Cliente> cliente = clienteRepository.findAll(pageable);
		if(cliente == null || cliente.isEmpty()) {
			throw new PersistenceException("Não existem dados para ser listado.");
		}
		clienteDto = modelMapper.map(cliente,  new TypeToken<Page<ClienteDto>>(){}.getType());
		return clienteDto;
	}
	
	private ClienteDto findByNome(String nome) {
		Cliente filtroCliente = clienteRepository.findByNomeIgnoreCase(nome);
		if (filtroCliente == null) {
			return null;
		}
		ClienteDto clienteDto = modelMapper.map(filtroCliente, ClienteDto.class);
		return clienteDto;
	}
	
	private List<ClienteDto> findByNomeLike(String nome) {
		List<Cliente> filtroCliente = clienteRepository.findByNomeContainingIgnoreCase(nome);
		List<ClienteDto> clienteDto = modelMapper.map(filtroCliente,  new TypeToken<List<ClienteDto>>(){}.getType());
		return clienteDto;
	}
	
	private Optional<Cliente> validarId(Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if (!cliente.isPresent()) {
			throw new PersistenceException("Cliente com id: " + id + " não encontrado.");
		}
		return cliente;
	}
	
	private void validarNome(String nome) {
		ClienteDto validaNome = findByNome(nome);
		if (validaNome != null) {
			throw new PersistenceException("Cliente já cadastrado.");
		}
	}

}
