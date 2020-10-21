package br.com.allesson.desafio.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.allesson.desafio.dto.ClienteDto;
import br.com.allesson.desafio.dto.ClienteInserirDto;
import br.com.allesson.desafio.entity.Endereco;
import br.com.allesson.desafio.service.ClienteService;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {
	
	private static String URI = "/cliente";
	
	@MockBean
	private ClienteService service;
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void inserirClienteTest() throws Exception {
		
		when(service.inserirCliente(ArgumentMatchers.any(ClienteInserirDto.class))).thenReturn(clienteDto());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
                .content("{\"nome\": \"Allesson Rodrigo\",\"sexo\": \"Masculino\",\"dataNasc\": \"1995-05-03\",\"idade\": 25,\"idEndereco\":  \"1\" }")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}
	
	@Test
	public void listarClientesTest() throws Exception {
		Page<ClienteDto> page = new PageImpl<ClienteDto>(listClienteDto());
		
		when(service.listarClientes(ArgumentMatchers.any(Integer.class))).thenReturn(page);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI+"/listar/0").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void filtrarIdTest() throws Exception {
		ClienteDto clienteDto = clienteDto();
		
		when(service.inserirCliente(ArgumentMatchers.any(ClienteInserirDto.class))).thenReturn(clienteDto);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI+"/1").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void atualizarNomeTest() throws Exception {
		ClienteDto clienteDto = clienteDto();
		
		when(service.inserirCliente(ArgumentMatchers.any(ClienteInserirDto.class))).thenReturn(clienteDto);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI+"/1").accept(MediaType.APPLICATION_JSON)
                .content("{\"nome\": \"Allesson Rodrigo\" }")
                .contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void filtrarNomeTest() throws Exception {
		when(service.filtrarNome(ArgumentMatchers.any(String.class))).thenReturn(listClienteDto());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON)
				.param("nome", "Allesson")
                .contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void deletarClienteTest() throws Exception {
		doNothing().when(service).deletarCliente(ArgumentMatchers.any(Long.class));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI + "/1").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
	}
	
	@Test
	public void inserirClienteErro() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
                .content("{\"sexo\": \"Masculino\",\"dataNasc\": \"1995-05-03\",\"idade\": 25,\"idEndereco\":  \"1\" }")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}
	
	private List<ClienteDto> listClienteDto(){
		List<ClienteDto> listclienteDto = new ArrayList<>();
		listclienteDto.add(clienteDto());
		return listclienteDto;
	}
	
	private ClienteDto clienteDto() {
		ClienteDto clienteDto = new ClienteDto();
		clienteDto.setId(1L);
		clienteDto.setNome("Allesson Rodrigo");
		clienteDto.setSexo("Masculino");
		clienteDto.setDataNasc(LocalDate.parse("1995-05-03"));
		clienteDto.setIdade(25);
		
		Endereco enderecoMock = new Endereco();
    	enderecoMock.setId(1L);
    	enderecoMock.setCidade("Recife");
    	enderecoMock.setEstado("Pernambuco");
    	clienteDto.setEndereco(enderecoMock);
    	
    	return clienteDto;
	}

}
