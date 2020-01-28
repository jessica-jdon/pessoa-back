package br.com.elotech.pessoa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.elotech.pessoa.entity.Pessoa;
import br.com.elotech.pessoa.service.PessoaService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PessoaController {

	@Autowired
	PessoaService servico;
	
    @GetMapping("/pessoas")
    private List<Pessoa> buscarTodasPessoas() {
        return servico.buscarTodasPessoas();
    }

    @GetMapping("/pessoas/{id}")
    private Pessoa buscarPessoa(@PathVariable("id") int id) {
        return servico.buscarPessoaPorId(id);
    }

    @DeleteMapping("/pessoas/{id}")
    private void excluir(@PathVariable("id") int id) {
    	servico.excluirPorId(id);
    }

    @PostMapping("/pessoas")
    private int salvar(@RequestBody Pessoa pessoa) {
    	servico.salvarOuAtualizar(pessoa);
        return pessoa.getId();
    }
}
