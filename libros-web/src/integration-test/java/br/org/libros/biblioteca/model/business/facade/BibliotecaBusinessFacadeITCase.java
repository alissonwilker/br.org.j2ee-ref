package br.org.libros.biblioteca.model.business.facade;

import static org.junit.Assert.fail;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.org.arquitetura.excecao.EntidadeJaExisteExcecao;
import br.org.arquitetura.excecao.EntidadeNaoEncontradaExcecao;
import br.org.libros.AbstractIntegrationTest;
import br.org.libros.biblioteca.dto.BibliotecaDto;

/**
 * Classe de teste que testa o comportamento da fachada negocial do módulo
 * Biblioteca.
 *
 */
@RunWith(Arquillian.class)
public class BibliotecaBusinessFacadeITCase extends AbstractIntegrationTest {
	@Inject
	private BibliotecaBusinessFacade bibliotecaBusinessFacade;

	/**
	 * Test method for
	 * {@link br.org.arquitetura.model.business.facade.AbstractBusinessFacade#adicionar(java.lang.Object)}.
	 */
	@Test
	public void testAdicionar() {
		BibliotecaDto bibliotecaDto = new BibliotecaDto("nome1");
		BibliotecaDto bibliotecaDtoResposta;
		try {
			bibliotecaDtoResposta = bibliotecaBusinessFacade.adicionar(bibliotecaDto);
			Assert.assertEquals(bibliotecaDto.getNome(), bibliotecaDtoResposta.getNome());
			Assert.assertNotNull(bibliotecaDtoResposta.getId());
		} catch (EntidadeJaExisteExcecao | EntidadeNaoEncontradaExcecao e) {
			fail("Entidade já existe ou não foi encontrada.");
		}
	}

}
