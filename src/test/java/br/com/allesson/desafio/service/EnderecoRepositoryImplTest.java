package br.com.allesson.desafio.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

import br.com.allesson.desafio.dto.EnderecoDto;
import br.com.allesson.desafio.dto.EnderecoInserirDto;
import br.com.allesson.desafio.entity.Endereco;
import br.com.allesson.desafio.repository.EnderecoRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ExtendWith(MockitoExtension.class)
public class EnderecoRepositoryImplTest {
	
	@MockBean
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    @Autowired
    private EnderecoServiceImpl service;
    
    @Test
    public void inserirEnderecoTest() {
    	
    	EnderecoInserirDto endereco = new EnderecoInserirDto();
    	endereco.setCidade("Recife");
    	endereco.setEstado("Pernambuco");
    	
    	when(enderecoRepository.save(ArgumentMatchers.any(Endereco.class))).thenReturn(getEndereco());
    	
    	assertNotNull(service.inserirEndereco(endereco));
    }
    
    @Test
    public void listarEnderecosTest() {
    	
    	Page <Endereco> pageMock = new PageImpl<Endereco>(getListEndereco());
    	
    	when(enderecoRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(pageMock);
    	
    	Page<EnderecoDto> page = service.listarEnderecos(0);
    	assertTrue(!page.getContent().isEmpty());
    }
    
    @Test
    public void filtrarCidadeTest() {
    	
    	when(enderecoRepository.findByCidadeIgnoreCase(ArgumentMatchers.any(String.class)))
    	.thenReturn(getListEndereco());
    	
    	List<EnderecoDto> enderecos = service.filtrarCidade("Olinda");
    	assertTrue(!enderecos.isEmpty());
    }
    
    @Test
    public void filtrarEstadoTest() {
    	
    	when(enderecoRepository.findByEstadoIgnoreCase(ArgumentMatchers.any(String.class)))
    	.thenReturn(getListEndereco());
    	
    	List<EnderecoDto> enderecos = service.filtrarEstado("Paraiba");
    	assertTrue(!enderecos.isEmpty());
    }
    
    @Test(expected = PersistenceException.class)
    public void inserirEnderecoErro() {
    	
    	EnderecoInserirDto endereco = new EnderecoInserirDto();
    	endereco.setCidade("Recife");
    	endereco.setEstado("Pernambuco");
    	
    	when(enderecoRepository.findByCidadeIgnoreCaseAndEstadoIgnoreCase(
    			ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class)))
    	.thenReturn(getEndereco());
    	service.inserirEndereco(endereco);
    }
    
    @Test(expected = PersistenceException.class)
    public void listarEnderecosErro() {
    	service.listarEnderecos(0);
    }
    
    @Test(expected = PersistenceException.class)
    public void filtrarCidadeErro() {
    	service.filtrarCidade("Bahia");
    }
    
    @Test(expected = PersistenceException.class)
    public void filtrarEstadoErro() {
    	service.filtrarEstado("São Paulo");
    }
    
    private List<Endereco> getListEndereco(){
    	List<Endereco> listEndereco = new ArrayList<>();
    	listEndereco.add(getEndereco());
    	return listEndereco;
    }
    
    private Endereco getEndereco() {
    	Endereco endereco = new Endereco();
    	endereco.setId(1L);
    	endereco.setCidade("Recife");
    	endereco.setEstado("Pernambuco");
    	return endereco;
    }

}
