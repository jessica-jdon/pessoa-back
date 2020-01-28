package br.com.elotech.pessoa.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.elotech.pessoa.entity.Pessoa;

public interface PessoaRepository extends CrudRepository<Pessoa, Integer> {
	
	@Query("select p from Pessoa p where nome = :nome")
	Pessoa buscaPorNome(String nome);
	}
