package br.com.allesson.desafio.service;

import java.util.List;

import javax.persistence.PersistenceException;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.allesson.desafio.dto.EnderecoDto;
import br.com.allesson.desafio.dto.EnderecoInserirDto;
import br.com.allesson.desafio.entity.Endereco;
import br.com.allesson.desafio.repository.EnderecoRepository;

@Service
public class EnderecoServiceImpl implements EnderecoService {
	
	private static final int TAMANHO_PAGINA = 10;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	public EnderecoDto inserirEndereco(EnderecoInserirDto endereco) {
		EnderecoDto enderecoList = findByCidadeAndEstado(endereco.getCidade(), endereco.getEstado());

		if (enderecoList != null) {
			throw new PersistenceException("Endereço já cadastrado.");
		}
		return  modelMapper.map(enderecoRepository.save(modelMapper.map(endereco, Endereco.class)),
				EnderecoDto.class);
	}
	
	public Page<EnderecoDto> listarEnderecos(int pagina){
		Page<EnderecoDto> retornoEnderecos = findAll(pagina);
		return retornoEnderecos;
	}
	
	public List<EnderecoDto> filtrarCidade(String cidade){
		List<EnderecoDto> enderecoDto = findByCidade(cidade);
		if(enderecoDto.isEmpty()) {
			throw new PersistenceException("Cidade não encontrada.");
		}
		return enderecoDto;
	}
	
	public List<EnderecoDto> filtrarEstado(String estado){
		List<EnderecoDto> enderecoDto = findByEstado(estado);
		if(enderecoDto.isEmpty()) {
			throw new PersistenceException("Estado não encontrado.");
		}
		return enderecoDto;
	}
	
	private EnderecoDto findByCidadeAndEstado(String cidade, String estado){
		Endereco endereco = enderecoRepository.findByCidadeIgnoreCaseAndEstadoIgnoreCase(cidade, estado);
		if (endereco == null) {
			return null;
		}
		return modelMapper.map(endereco, EnderecoDto.class);
	}
	
	private Page<EnderecoDto> findAll(int pagina) {
		Page<EnderecoDto> enderecoDto;
		PageRequest pageable = PageRequest.of(pagina, TAMANHO_PAGINA, Sort.Direction.ASC,
                "id");
		Page<Endereco> endereco = enderecoRepository.findAll(pageable);
		if(endereco.isEmpty()) {
			throw new PersistenceException("Não existem dados para ser listado.");
		}
		enderecoDto = modelMapper.map(endereco,  new TypeToken<Page<EnderecoDto>>(){}.getType());
		return enderecoDto;
	}
	
	private List<EnderecoDto> findByCidade(String cidade){
		List<Endereco> enderecos = enderecoRepository.findByCidadeIgnoreCase(cidade);
		
		return modelMapper.map(enderecos, new TypeToken<List<EnderecoDto>>(){}.getType());
	}
	
	private List<EnderecoDto> findByEstado(String estado){
		List<Endereco> enderecos = enderecoRepository.findByEstadoIgnoreCase(estado);
		
		return modelMapper.map(enderecos, new TypeToken<List<EnderecoDto>>(){}.getType());
	}

}
