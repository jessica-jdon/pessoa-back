package br.com.elotech.pessoa.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.elotech.pessoa.PessoaApplication;
import br.com.elotech.pessoa.entity.Pessoa;
import br.com.elotech.pessoa.repository.PessoaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PessoaApplication.class)
@AutoConfigureMockMvc 
@AutoConfigureTestDatabase
public class PessoaControllerTest {

	
	@Autowired
    private MockMvc mvc;

    @Autowired
    private PessoaRepository repositorio;

    @After
    public void resetDb() {
    	repositorio.deleteAll();
    }
    
    @Test
    public void quandoValoresValidos_entaoCriaPessoa() throws IOException, Exception {
        Pessoa pessoa = new Pessoa("Jessica", "00964585906");
        mvc.perform(post("/pessoas").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(pessoa)));

        List<Pessoa> found = (List<Pessoa>) repositorio.findAll();
        assertThat(found).extracting(Pessoa::getNome).containsOnly("Jessica");
    }

    @Test
    public void devolvePessoas_quandoBuscaPessoas_entaoStatus200() throws Exception {
    	criaTestPessoa("Pessoa1", "12309834578");
    	criaTestPessoa("Pessoa2", "00300984578");

        mvc.perform(get("/pessoas").contentType(MediaType.APPLICATION_JSON))
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[0].nome", is("Pessoa1")))
          .andExpect(jsonPath("$[1].nome", is("Pessoa2")));
    }
    
    private void criaTestPessoa(String nome, String cpf) {
        Pessoa pessoa = new Pessoa(nome, cpf);
        repositorio.save(pessoa);
    }



}
