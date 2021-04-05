package io.platformbuilders.client_api.integracao;

import io.platformbuilders.client_api.domain.cliente.Cliente;
import io.platformbuilders.client_api.domain.cliente.repository.ClienteRepository;
import io.platformbuilders.client_api.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ClienteTest extends IntegrationTestConfiguration {

    @Autowired
    private ClienteRepository clienteRepository;

    private String clienteJson;
    private Cliente cliente, cliente2;

    @Before
    public void setUp() {
        super.setUp();
        RestAssured.basePath = "/clientes";
        clienteJson = ResourceUtils.getContentFromResource("/json/criar-cliente.json");
        prepararDados();
    }

    private void prepararDados() {
        Cliente cliente = new Cliente("VICTOR PIETRO", "VICTOR_PIETRO@HOTMAIL.COM", LocalDate.of(1995, 7, 6));
        this.cliente = clienteRepository.save(cliente);

        Cliente cliente2 = new Cliente("GUILHERME LINO", "GUILHERMINO.LINO@HOTMAIL.COM", LocalDate.of(2000, 10, 5));
        this.cliente2 = clienteRepository.save(cliente2);
    }

    @Test
    public void cadastrarCliente_Retornando201() {
        String payload = clienteJson
                .replace("{{nome}}", "GUILHERME ARRUDA")
                .replace("{{email}}", "GUILHERMINO@HOTMAIL.COM")
                .replace("{{dataNascimento}}", "2000-05-04");

        Response response = given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post();
        response.then()
                .statusCode(HttpStatus.CREATED.value());

        String id = getIdHeaderLocation(response);

        given()
                .pathParam("clienteId", id)
                .when()
                .get("/{clienteId}")
                .then()
                .body("size()", is(5))
                .body("id", is(id))
                .body("nome", is("GUILHERME ARRUDA"))
                .body("email", is("GUILHERMINO@HOTMAIL.COM"))
                .body("dataNascimento", is("2000-05-04"))
                .body("idade", is(20))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void cadastrarCliente_EmailEmUso_Retornando400() {
        String payload = clienteJson
                .replace("{{nome}}", "VICTOR PIETRO REIS")
                .replace("{{email}}", "VICTOR_PIETRO@HOTMAIL.COM")
                .replace("{{dataNascimento}}", "1995-07-06");

        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .body("mensagem", is("O E-MAIL VICTOR_PIETRO@HOTMAIL.COM JÁ ESTÁ EM USO."))
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    public void cadastrarCliente_EmailEmUsoMinusculo_Retornando400() {
        String payload = clienteJson
                .replace("{{nome}}", "VICTOR PIETRO REIS")
                .replace("{{email}}", "victor_pietro@hotmail.com")
                .replace("{{dataNascimento}}", "1995-07-06");

        given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .body("mensagem", is("O E-MAIL VICTOR_PIETRO@HOTMAIL.COM JÁ ESTÁ EM USO."))
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    public void atualizarCliente_Retornando204() {
        String payload = clienteJson
                .replace("{{nome}}", "VICTOR PIETRO REIS")
                .replace("{{email}}", "VICTOR_PIETRO@HOTMAIL.COM")
                .replace("{{dataNascimento}}", "1995-07-06");

        given()
                .pathParam("clienteId", cliente.getId().toString())
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/{clienteId}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void atualizarCliente_EmailEmUso_Retornando204() {
        String payload = clienteJson
                .replace("{{nome}}", "GUILERMINO LINO")
                .replace("{{email}}", "VICTOR_PIETRO@HOTMAIL.COM")
                .replace("{{dataNascimento}}", "2000-10-05");

        given()
                .pathParam("clienteId", cliente2.getId().toString())
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/{clienteId}")
                .then()
                .body("mensagem", is("O E-MAIL VICTOR_PIETRO@HOTMAIL.COM JÁ ESTÁ EM USO."))
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    public void atualizarCliente_EmailEmUsoMinusculo_Retornando204() {
        String payload = clienteJson
                .replace("{{nome}}", "GUILERMINO LINO")
                .replace("{{email}}", "victor_pietro@hotmail.com")
                .replace("{{dataNascimento}}", "2000-10-05");

        given()
                .pathParam("clienteId", cliente2.getId().toString())
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/{clienteId}")
                .then()
                .body("mensagem", is("O E-MAIL VICTOR_PIETRO@HOTMAIL.COM JÁ ESTÁ EM USO."))
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    public void deletarCliente_Retornando204() {
        given()
                .pathParam("clienteId", cliente.getId().toString())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete("/{clienteId}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void findById_Retornando200() {
        given()
                .pathParam("clienteId", cliente.getId().toString())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("/{clienteId}")
                .then()
                .body("size()", is(5))
                .body("id", is(cliente.getId().toString()))
                .body("nome", is("VICTOR PIETRO"))
                .body("email", is("VICTOR_PIETRO@HOTMAIL.COM"))
                .body("dataNascimento", is("1995-07-06"))
                .body("idade", is(25))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findById_ClienteNaoEncontrado() {
        given()
                .pathParam("clienteId", "c504ad9b-730c-4c7b-8149-b4dc95d68520")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("/{clienteId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void buscaPaginada_ParametroNome() {
        given()
                .param("orderBy", "nome")
                .param("direction", "ASC")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("totalElements", is(2))
                .body("$", hasKey("content"))
                .body("content", everyItem(hasKey("id")))
                .body("content", everyItem(hasKey("nome")))
                .body("content", everyItem(hasKey("email")))
                .body("content", everyItem(hasKey("dataNascimento")))
                .body("content", everyItem(hasKey("idade")))
                .body("content[0].id", is(cliente2.getId().toString()))
                .body("content[0].nome", is("GUILHERME LINO"))
                .body("content[0].email", is("GUILHERMINO.LINO@HOTMAIL.COM"))
                .body("content[0].dataNascimento", is("2000-10-05"))
                .body("content[0].idade", is(20))
                .body("content[1].id", is(cliente.getId().toString()))
                .body("content[1].nome", is("VICTOR PIETRO"))
                .body("content[1].email", is("VICTOR_PIETRO@HOTMAIL.COM"))
                .body("content[1].dataNascimento", is("1995-07-06"))
                .body("content[1].idade", is(25))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void buscaPaginada_ParametroEmail() {
        given()
                .param("orderBy", "email")
                .param("direction", "ASC")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("totalElements", is(2))
                .body("$", hasKey("content"))
                .body("content", everyItem(hasKey("id")))
                .body("content", everyItem(hasKey("nome")))
                .body("content", everyItem(hasKey("email")))
                .body("content", everyItem(hasKey("dataNascimento")))
                .body("content", everyItem(hasKey("idade")))
                .body("content[0].id", is(cliente2.getId().toString()))
                .body("content[0].nome", is("GUILHERME LINO"))
                .body("content[0].email", is("GUILHERMINO.LINO@HOTMAIL.COM"))
                .body("content[0].dataNascimento", is("2000-10-05"))
                .body("content[0].idade", is(20))
                .body("content[1].id", is(cliente.getId().toString()))
                .body("content[1].nome", is("VICTOR PIETRO"))
                .body("content[1].email", is("VICTOR_PIETRO@HOTMAIL.COM"))
                .body("content[1].dataNascimento", is("1995-07-06"))
                .body("content[1].idade", is(25))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void buscaPaginada_ParametroDataNascimento() {
        given()
                .param("orderBy", "dataNascimento")
                .param("direction", "ASC")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("totalElements", is(2))
                .body("$", hasKey("content"))
                .body("content", everyItem(hasKey("id")))
                .body("content", everyItem(hasKey("nome")))
                .body("content", everyItem(hasKey("email")))
                .body("content", everyItem(hasKey("dataNascimento")))
                .body("content", everyItem(hasKey("idade")))
                .body("content[0].id", is(cliente.getId().toString()))
                .body("content[0].nome", is("VICTOR PIETRO"))
                .body("content[0].email", is("VICTOR_PIETRO@HOTMAIL.COM"))
                .body("content[0].dataNascimento", is("1995-07-06"))
                .body("content[0].idade", is(25))
                .body("content[1].id", is(cliente2.getId().toString()))
                .body("content[1].nome", is("GUILHERME LINO"))
                .body("content[1].email", is("GUILHERMINO.LINO@HOTMAIL.COM"))
                .body("content[1].dataNascimento", is("2000-10-05"))
                .body("content[1].idade", is(20))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void buscaPaginada_ParametroIdade() {
        given()
                .param("orderBy", "idade")
                .param("direction", "ASC")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("totalElements", is(2))
                .body("$", hasKey("content"))
                .body("content", everyItem(hasKey("id")))
                .body("content", everyItem(hasKey("nome")))
                .body("content", everyItem(hasKey("email")))
                .body("content", everyItem(hasKey("dataNascimento")))
                .body("content", everyItem(hasKey("idade")))
                .body("content[0].id", is(cliente2.getId().toString()))
                .body("content[0].nome", is("GUILHERME LINO"))
                .body("content[0].email", is("GUILHERMINO.LINO@HOTMAIL.COM"))
                .body("content[0].dataNascimento", is("2000-10-05"))
                .body("content[0].idade", is(20))
                .body("content[1].id", is(cliente.getId().toString()))
                .body("content[1].nome", is("VICTOR PIETRO"))
                .body("content[1].email", is("VICTOR_PIETRO@HOTMAIL.COM"))
                .body("content[1].dataNascimento", is("1995-07-06"))
                .body("content[1].idade", is(25))
                .statusCode(HttpStatus.OK.value());
    }

}
