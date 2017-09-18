package br.org.libros.negocio.biblioteca.model.business;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.org.arquitetura.excecao.EntidadeJaExisteExcecao;
import br.org.arquitetura.excecao.EntidadeNaoEncontradaExcecao;
import br.org.arquitetura.model.business.AbstractBusiness;
import br.org.libros.negocio.biblioteca.model.persistence.entity.Biblioteca;
import br.org.libros.negocio.livrobiblioteca.model.persistence.entity.LivroBiblioteca;

/**
 * 
 * @see br.org.arquitetura.model.business.AbstractBusiness
 */
@Named
@RequestScoped
public class BibliotecaBusiness extends AbstractBusiness<Biblioteca, Integer> {

    private static final long serialVersionUID = 1L;

    @Override
    public Biblioteca atualizar(Biblioteca entidade) throws EntidadeJaExisteExcecao, EntidadeNaoEncontradaExcecao {
        verificarExistenciaLivros(entidade);
        return super.atualizar(entidade);
    }

    @Override
    public Biblioteca adicionar(Biblioteca entidade) throws EntidadeJaExisteExcecao, EntidadeNaoEncontradaExcecao {
        verificarExistenciaLivros(entidade);
        return super.atualizar(entidade);
    }

    private void verificarExistenciaLivros(Biblioteca entidade) throws EntidadeNaoEncontradaExcecao {
        if (entidade.getLivros() != null) {
            for (LivroBiblioteca livro : entidade.getLivros()) {
                // TODO substituir endereço hardcoded por um service discovery
                Client client = ClientBuilder.newClient();
                Response response = client.target("http://localhost:8080/livraria/api/livros/" + livro.getId())
                        .request(MediaType.APPLICATION_JSON).get();
                if (Status.fromStatusCode(response.getStatus()) != Status.OK) {
                    throw new EntidadeNaoEncontradaExcecao();
                }
            }
        }
    }

}
