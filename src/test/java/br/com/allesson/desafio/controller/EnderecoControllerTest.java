package br.com.allesson.desafio.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

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

import br.com.allesson.desafio.dto.EnderecoDto;
import br.com.allesson.desafio.dto.EnderecoInserirDto;
import br.com.allesson.desafio.service.EnderecoService;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class EnderecoControllerTest {
	
private static String URI = "/endereco";
	
	@MockBean
	private EnderecoService service;
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void inserirEnderecoTest() throws Exception {
		
		when(service.inserirEndereco(ArgumentMatchers.any(EnderecoInserirDto.class))).thenReturn(enderecoDto());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
                .content("{\"cidade\": \"Olinda\",\"estado\": \"Pernambuco\" }")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}
	
	@Test
	public void listarEnderecosTest() throws Exception {
		Page<EnderecoDto> page = new PageImpl<EnderecoDto>(listEnderecoDto());
		
		when(service.listarEnderecos(ArgumentMatchers.any(Integer.class))).thenReturn(page);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI+"/listar/0").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void filtrarCidadeTest() throws Exception {
		when(service.filtrarCidade(ArgumentMatchers.any(String.class))).thenReturn(listEnderecoDto());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI + "/consultar-cidade").accept(MediaType.APPLICATION_JSON)
				.param("cidade", "Olinda")
                .contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void filtrarEstadoTest() throws Exception {
		when(service.filtrarEstado(ArgumentMatchers.any(String.class))).thenReturn(listEnderecoDto());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI + "/consultar-estado").accept(MediaType.APPLICATION_JSON)
				.param("estado", "Pernambuco")
                .contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	private List<EnderecoDto> listEnderecoDto(){
		List<EnderecoDto> listEnderecoDto = new ArrayList<>();
		listEnderecoDto.add(enderecoDto());
		return listEnderecoDto;
	}
	
	private EnderecoDto enderecoDto() {
		EnderecoDto enderecoDto = new EnderecoDto();
		enderecoDto.setId(1L);
		enderecoDto.setCidade("Olinda");
		enderecoDto.setEstado("Pernambuco");
		return enderecoDto;
	}

}
