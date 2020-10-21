package br.com.allesson.desafio.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.allesson.desafio.dto.EnderecoDto;
import br.com.allesson.desafio.dto.EnderecoInserirDto;
import br.com.allesson.desafio.service.EnderecoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/endereco")
@Api(value = "ENDERECO REST API")
public class EnderecoController {
	
	@Autowired
	private EnderecoService service;
	
	@PostMapping
	@ApiOperation(value = "Cadastrar cidade")
	public ResponseEntity<EnderecoDto> inserirEndereco(@RequestBody @Valid EnderecoInserirDto obj){
		EnderecoDto enderecoDto = service.inserirEndereco(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(enderecoDto.getId()).toUri();
		return ResponseEntity.created(uri).body(enderecoDto);
	}
	
	@GetMapping("listar/{pagina}")
	@ApiOperation(value = "Listar Enderecos")
	public ResponseEntity<Page<EnderecoDto>> listarEnderecos(@PathVariable int pagina){
		return ResponseEntity.ok().body(service.listarEnderecos(pagina));
	}
	
	@GetMapping(value = "/consultar-cidade")
	@ApiOperation(value = "Consultar cidade pelo nome")
	public ResponseEntity<List<EnderecoDto>> filtrarCidade(@RequestParam(value = "cidade", required = true) String cidade){
		return ResponseEntity.ok().body(service.filtrarCidade(cidade));
		
	}
	
	@GetMapping(value = "/consultar-estado")
	@ApiOperation(value = "Consultar cidade pelo estado")
	public ResponseEntity<List<EnderecoDto>> filtrarEstado(@RequestParam(value = "estado", required = true) String estado){
		return ResponseEntity.ok().body(service.filtrarEstado(estado));
		
	}

}
