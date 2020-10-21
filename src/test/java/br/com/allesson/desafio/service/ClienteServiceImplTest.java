package br.com.allesson.desafio.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.allesson.desafio.dto.ClienteAtualizarDto;
import br.com.allesson.desafio.dto.ClienteDto;
import br.com.allesson.desafio.dto.ClienteInserirDto;
import br.com.allesson.desafio.entity.Cliente;
import br.com.allesson.desafio.entity.Endereco;
import br.com.allesson.desafio.repository.ClienteRepository;
import br.com.allesson.desafio.repository.EnderecoRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {
	@MockBean
    private ClienteRepository clienteRepository;
	
	@MockBean
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    @Autowired
    private ClienteServiceImpl service;
    
    @Test
    public void inserirClienteTest() {
    	ClienteInserirDto cliente = new ClienteInserirDto();
    	cliente.setNome("Allesson Rodrigo");
    	cliente.setSexo("masculino");
    	cliente.setDataNasc(LocalDate.parse("1995-05-03"));
    	cliente.setIdade(25);
    	cliente.setIdEndereco(1L);
    	
    	Optional<Endereco> opEndereco = Optional.of(getCliente().getEndereco());
    	
    	when(enderecoRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(opEndereco);
    	when(clienteRepository.save(ArgumentMatchers.any(Cliente.class))).thenReturn(getCliente());
    	assertNotNull(service.inserirCliente(cliente));
    }
    
    @Test
    public void listarClientesTest() {
    	
    	Page <Cliente> pageMock = new PageImpl<Cliente>(getListCliente());
    	when(clienteRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(pageMock);
    	Page<ClienteDto> page = service.listarClientes(0);
    	assertTrue(!page.getContent().isEmpty());
    }
    
    @Test
    public void filtrarIdTest() {
    	
    	Optional<Cliente> opCliente = Optional.of(getCliente());
    	
    	when(clienteRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(opCliente);
    	
    	ClienteDto clienteDto = service.filtrarId(1L);
    	
    	assertEquals(getCliente().getId(), clienteDto.getId());
    	assertEquals(getCliente().getNome(), clienteDto.getNome());
    	assertEquals(getCliente().getSexo(), clienteDto.getSexo());
    	assertEquals(getCliente().getDataNasc(), clienteDto.getDataNasc());
    	assertEquals(getCliente().getIdade(), clienteDto.getIdade());
    	assertEquals(getCliente().getEndereco().getId(), clienteDto.getEndereco().getId());
    	assertEquals(getCliente().getEndereco().getCidade(), clienteDto.getEndereco().getCidade());
    	assertEquals(getCliente().getEndereco().getEstado(), clienteDto.getEndereco().getEstado());
    }
    
    @Test
    public void filtrarNomeTest() {
    	
    	when(clienteRepository.findByNomeContainingIgnoreCase(ArgumentMatchers.any(String.class)))
    	.thenReturn(getListCliente());
    	
    	List<ClienteDto> listCliente = service.filtrarNome("Alle");
    	
    	assertTrue(!listCliente.isEmpty());
    }
    
    @Test
    public void atualizarNomeClienteTest() {
    	
    	Optional<Cliente> opCliente = Optional.of(getCliente());
    	
    	when(clienteRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(opCliente);
    	
    	when(clienteRepository.getOne(ArgumentMatchers.any(Long.class))).thenReturn(getCliente());
    	
    	when(clienteRepository.save(ArgumentMatchers.any(Cliente.class))).thenReturn(getCliente());
    	
    	ClienteAtualizarDto clienteAtualizarDto = new ClienteAtualizarDto();
    	clienteAtualizarDto.setNome("Allesson Rodrigo");
    	
    	ClienteDto clienteDto = service.atualizarNomeCliente(1L, clienteAtualizarDto);
    	
    	assertEquals(getCliente().getId(), clienteDto.getId());
    	assertEquals(getCliente().getNome(), clienteDto.getNome());
    	assertEquals(getCliente().getSexo(), clienteDto.getSexo());
    	assertEquals(getCliente().getDataNasc(), clienteDto.getDataNasc());
    	assertEquals(getCliente().getIdade(), clienteDto.getIdade());
    	assertEquals(getCliente().getEndereco().getId(), clienteDto.getEndereco().getId());
    	assertEquals(getCliente().getEndereco().getCidade(), clienteDto.getEndereco().getCidade());
    	assertEquals(getCliente().getEndereco().getEstado(), clienteDto.getEndereco().getEstado());
    }
    
    @Test
    public void inserirClienteErro() {
    	ClienteInserirDto cliente = new ClienteInserirDto();
    	cliente.setNome("Allesson Rodrigo");
    	cliente.setSexo("masculino");
    	cliente.setDataNasc(LocalDate.parse("1995-05-03"));
    	cliente.setIdade(25);
    	cliente.setIdEndereco(1L);
    	
    	assertThrows(PersistenceException.class, () -> {
    		service.inserirCliente(cliente);
        });
    }
    
    @Test
    public void listarClientesErro() {
    	assertThrows(PersistenceException.class, () -> {
    		service.listarClientes(0);
    	});
    }
    
    @Test
    public void filtrarIdErro() {
    	assertThrows(PersistenceException.class, () -> {
    		service.filtrarId(1L);
    	});
    }
    
    @Test
    public void filtrarNomeErro() {
    	assertThrows(PersistenceException.class, () -> {
    		service.filtrarNome("Alle");
    	});
    }
    
    @Test
    public void deletarClienteErro() {
    	assertThrows(PersistenceException.class, () -> {
    		service.deletarCliente(1L);
    	});
    }
    
    private List<Cliente> getListCliente(){
    	List<Cliente> listCliente = new ArrayList<>();
    	listCliente.add(getCliente());
    	return listCliente;
    }
    
    private Cliente getCliente() {
    	Endereco endereco = new Endereco();
    	endereco.setId(1L);
    	endereco.setCidade("Recife");
    	endereco.setEstado("Pernambuco"); 
    	
    	Cliente cliente = new Cliente();
    	cliente.setId(1L);
    	cliente.setNome("Allesson Rodrigo");
    	cliente.setSexo("Masculino");
    	cliente.setDataNasc(LocalDate.parse("1995-05-03"));
    	cliente.setIdade(25);
    	cliente.setEndereco(endereco);
    	
    	return cliente;
    }

}