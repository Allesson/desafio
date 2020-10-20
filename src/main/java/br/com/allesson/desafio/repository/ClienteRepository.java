package br.com.allesson.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.allesson.desafio.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	Cliente findByNomeIgnoreCase(String nome);
	List<Cliente> findByNomeContainingIgnoreCase(String nome);

}
