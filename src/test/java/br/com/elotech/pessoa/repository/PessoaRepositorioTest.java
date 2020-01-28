package br.com.elotech.pessoa.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.elotech.pessoa.entity.Pessoa;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PessoaRepositorioTest {

	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PessoaRepository repositorio;

    @Test
    public void qunadoCrioPessoa_entaoRetornaPessoa() {
        Pessoa pessoa = new Pessoa("Pessoa 0", "00896585906");
        entityManager.persistAndFlush(pessoa);

        Pessoa pessoaDoBanco = repositorio.buscaPorNome(pessoa.getNome());
        assertThat(pessoaDoBanco).isNotNull();
    }

    @Test
    public void qunadoBuscaPorNome_entaoRetornaPessoa() {
        Pessoa jessica = new Pessoa("Jessica", "00964585906");
        entityManager.persistAndFlush(jessica);

        Pessoa pessoa = repositorio.buscaPorNome(jessica.getNome());
        assertThat(pessoa.getNome()).isEqualTo(pessoa.getNome());
    }

    @Test
    public void quandoNomeInvalido_entaoRetonaNulo() {
    	Pessoa invalido = repositorio.buscaPorNome("nomeInvalido");
        assertThat(invalido).isNull();
    }

    @Test
    public void qunadoBuscaPorId_entaoRetornaPessoa() {
    	Pessoa pessoa = new Pessoa("Pessoa1", "00964798056");
        entityManager.persistAndFlush(pessoa);

        Pessoa doBanco = repositorio.findById(pessoa.getId()).orElse(null);
        assertThat(doBanco.getNome()).isEqualTo(pessoa.getNome());
    }

    @Test
    public void quandoIdInvalido_entaoRetonaNulo() {
    	Pessoa doBanco = repositorio.findById(-11).orElse(null);
        assertThat(doBanco).isNull();
    }

    @Test
    public void dado3Pessoas_quandoBuscaTodas_entaoRetornaTodas() {
    	Pessoa pessoa1 = new Pessoa("Pessoa1", "12309834578");
    	Pessoa pessoa2 = new Pessoa("Pessoa2", "45609834578");
    	Pessoa pessoa3 = new Pessoa("Pessoa3", "78909834578");

        entityManager.persist(pessoa1);
        entityManager.persist(pessoa2);
        entityManager.persist(pessoa3);
        entityManager.flush();

        List<Pessoa> todasPessoas = (List<Pessoa>) repositorio.findAll();

        assertThat(todasPessoas).hasSize(3).extracting(Pessoa::getNome).containsOnly(pessoa1.getNome(), pessoa2.getNome(), pessoa3.getNome());
    }
    
    @Test
    public void quandoEditoPessoa_entaoRetornaPessoaEditada() {
    	Pessoa pessoa4 = new Pessoa("Pessoa4", "09809834578");

        entityManager.persist(pessoa4);
        entityManager.flush();
        
        Pessoa pessoaEditada = repositorio.buscaPorNome(pessoa4.getNome());
        
        pessoaEditada.setNome("Nova pessoa");
        
        entityManager.persist(pessoaEditada);
        entityManager.flush();

        Pessoa doBanco = repositorio.buscaPorNome(pessoaEditada.getNome());

        assertThat(doBanco.getNome()).isEqualTo(pessoaEditada.getNome());
    }
    
    @Test
    public void quandoExcluoPessoa_entaoRetornaNulo() {
    	Pessoa pessoa5 = new Pessoa("Pessoa5", "09809876543");

        entityManager.persist(pessoa5);
        entityManager.flush();
        
        repositorio.delete(pessoa5);

        Pessoa doBanco = repositorio.buscaPorNome(pessoa5.getNome());

        assertThat(doBanco).isNull();
    }
    

}
