package br.org.libros.web.biblioteca.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

import org.apache.http.HttpStatus;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.org.libros.negocio.biblioteca.api.BibliotecaApi;
import br.org.libros.negocio.biblioteca.dto.BibliotecaDto;
import br.org.libros.web.AbstractIntegrationTest;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Classe de teste que testa o comportamento da API do módulo Biblioteca.
 *
 */
@RunWith(Arquillian.class)
public class BibliotecaApiITCase extends AbstractIntegrationTest {
    private static final String BASE_HREF = "/" + TEST_APP_CONTEXT_ROOT + "/api" + BibliotecaApi.PATH;
    private static final RequestSpecification request = given().contentType(ContentType.JSON);

    /**
     * Test method for
     * {@link br.org.libros.negocio.biblioteca.api.BibliotecaApi#adicionar(java.lang.String)}
     * and {@link br.org.libros.negocio.biblioteca.api.BibliotecaApi#listar()}.
     */
    @Test
    public void testAdicionarEListar() {
        BibliotecaDto dto = new BibliotecaDto();
        dto.setNome("nova biblioteca");
        request.body(dto).post(BASE_HREF).then().statusCode(HttpStatus.SC_CREATED);
        request.get(BASE_HREF).then().body("id", hasItems(1));
    }

}
