package br.com.allesson.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.allesson.desafio.entity.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
	
	Endereco findByCidadeIgnoreCaseAndEstadoIgnoreCase(String cidade, String estado);
	
	List<Endereco> findByCidadeIgnoreCase(String cidade);
	
	List<Endereco> findByEstadoIgnoreCase(String estado);

}
