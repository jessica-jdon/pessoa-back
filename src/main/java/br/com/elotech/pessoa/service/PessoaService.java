package br.com.elotech.pessoa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.elotech.pessoa.entity.Pessoa;
import br.com.elotech.pessoa.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
    PessoaRepository repositorio;

    public List<Pessoa> buscarTodasPessoas() {
        List<Pessoa> pessoas = new ArrayList<Pessoa>();
        repositorio.findAll().forEach(pessoa -> pessoas.add(pessoa));
        return pessoas;
    }

    public Pessoa buscarPessoaPorId(int id) {
        return repositorio.findById(id).orElse(null);
    }

    public void salvarOuAtualizar(Pessoa pessoa) {
    	repositorio.save(pessoa);
    }

    public void excluirPorId(int id) {
    	repositorio.deleteById(id);
    }
    
    public Pessoa buscaPessoaPorNome(String nome) {
    	return repositorio.buscaPorNome(nome);
    }

}
