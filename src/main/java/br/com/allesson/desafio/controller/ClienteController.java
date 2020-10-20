package br.com.allesson.desafio.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.allesson.desafio.dto.ClienteAtualizarDto;
import br.com.allesson.desafio.dto.ClienteDto;
import br.com.allesson.desafio.dto.ClienteInserirDto;
import br.com.allesson.desafio.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/cliente")
@Api(value = "CLIENTE REST API")
public class ClienteController {
	
	@Autowired
	private ClienteService service;
	
	@PostMapping
	@ApiOperation(value = "Insere um novo cliente")
	public ResponseEntity<ClienteDto> insert(@RequestBody @Valid ClienteInserirDto obj){
		ClienteDto clienteDto = service.inserirCliente(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(clienteDto.getId()).toUri();
		return ResponseEntity.created(uri).body(clienteDto);
	}
	
	@GetMapping("listar/{pagina}")
	@ApiOperation(value = "Listar clientes")
	public ResponseEntity<Page<ClienteDto>> listarClientes(@PathVariable int pagina){
		return ResponseEntity.ok().body(service.listarClientes(pagina));
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Consultar cliente pelo Id")
	public ResponseEntity<ClienteDto> filtrarId(@PathVariable Long id){
		return ResponseEntity.ok().body(service.filtrarId(id));
	}
	
	@GetMapping
	@ApiOperation(value = "Consultar cliente pelo nome")
	public ResponseEntity<List<ClienteDto>> filtrarNome(@RequestParam(value = "nome", defaultValue = "") String nome){
		return ResponseEntity.ok().body(service.filtrarNome(nome));
	}
	
	@PutMapping(value = "/{id}")
	@ApiOperation(value = "Alterar o nome do cliente")
	public ResponseEntity<ClienteDto> atualizarNome(@PathVariable Long id, @RequestBody @Valid ClienteAtualizarDto nome){
		ClienteDto obj = service.atualizarNomeCliente(id, nome);
		return ResponseEntity.ok().body(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Remover cliente")
	public ResponseEntity<Void> deletarCliente(@PathVariable Long id){
		service.deletarCliente(id);
		return ResponseEntity.noContent().build();
	}

}
