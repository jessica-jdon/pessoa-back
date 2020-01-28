package br.com.elotech.pessoa.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.elotech.pessoa.entity.Pessoa;
import br.com.elotech.pessoa.repository.PessoaRepository;

@RunWith(SpringRunner.class)
public class PessoaServiceTest {

	@TestConfiguration
	static class PessoaServiceTestContextConfiguration {

		@Bean
		public PessoaService pessoaService() {
			return new PessoaService();
		}
	}

	@Autowired
	private PessoaService servico;

	@MockBean
	private PessoaRepository repositorio;

	@Before
	public void setUp() {
		Pessoa pessoaA = new Pessoa("PessoaA", "12309834578");
		pessoaA.setId(11);

		Pessoa pessoaB = new Pessoa("PessoaB", "12309590578");
		Pessoa pessoaC = new Pessoa("PessoaC", "45609834578");

		List<Pessoa> todasPessoas = Arrays.asList(pessoaA, pessoaB, pessoaC);

		Mockito.when(repositorio.buscaPorNome(pessoaA.getNome())).thenReturn(pessoaA);
		Mockito.when(repositorio.buscaPorNome(pessoaB.getNome())).thenReturn(pessoaB);
		Mockito.when(repositorio.buscaPorNome("invalido")).thenReturn(null);
		Mockito.when(repositorio.findById(pessoaA.getId())).thenReturn(Optional.of(pessoaA));
		Mockito.when(repositorio.findAll()).thenReturn(todasPessoas);
		Mockito.when(repositorio.findById(-99)).thenReturn(Optional.empty());
	}

	@Test
	public void qunadoBuscaPorNome_entaoRetornaPessoa() {
		String name = "PessoaB";
		Pessoa busca = servico.buscaPessoaPorNome(name);

		assertThat(busca.getNome()).isEqualTo(name);
	}

	@Test
	public void quandoNomeInvalido_entaoRetonaNulo() {
		Pessoa doBanco = servico.buscaPessoaPorNome("invalido");
		assertThat(doBanco).isNull();

		chamaBuscaPorNomeUmaVez("invalido");
	}

	@Test
	public void qunadoBuscaPorId_entaoRetornaPessoa() {
		Pessoa doBanco = servico.buscarPessoaPorId(11);
		assertThat(doBanco.getNome()).isEqualTo("PessoaA");

		chamaBuscaPorIdUmaVez();
	}

	@Test
	public void quandoIdInvalido_entaoRetonaNulo() {
		Pessoa doBanco = servico.buscarPessoaPorId(-99);
		chamaBuscaPorIdUmaVez();
		
		assertThat(doBanco).isNull();
	}

	@Test
	public void dado3Pessoas_quandoBuscaTodas_entaoRetornaTodas() {
		Pessoa pessoaA = new Pessoa("PessoaA", "12309834578");
		Pessoa pessoaB = new Pessoa("PessoaB", "12309590578");
		Pessoa pessoaC = new Pessoa("PessoaC", "45609834578");

		List<Pessoa> todasPessoas = servico.buscarTodasPessoas();
		chamaBuscaTodasPessoasUmaVez();
		assertThat(todasPessoas).hasSize(3).extracting(Pessoa::getNome).contains(pessoaA.getNome(), pessoaB.getNome(),
				pessoaC.getNome());
	}

	private void chamaBuscaPorNomeUmaVez(String nome) {
		Mockito.verify(repositorio, VerificationModeFactory.times(1)).buscaPorNome(nome);
		Mockito.reset(repositorio);
	}

	private void chamaBuscaPorIdUmaVez() {
		Mockito.verify(repositorio, VerificationModeFactory.times(1)).findById(Mockito.anyInt());
		Mockito.reset(repositorio);
	}

	private void chamaBuscaTodasPessoasUmaVez() {
		Mockito.verify(repositorio, VerificationModeFactory.times(1)).findAll();
		Mockito.reset(repositorio);
	}

}
