package io.platformbuilders.client_api.integracao;

import io.platformbuilders.client_api.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ClienteDTOTest extends IntegrationTestConfiguration {

    private String clienteCriar;

    @Before
    public void setUp() {
        super.setUp();
        RestAssured.basePath = "/clientes";
        clienteCriar = ResourceUtils.getContentFromResource("/json/criar-cliente.json");
    }

    @Test
    public void criarErro_NomeNulo_Retornando400() {
        String criarCliente = clienteCriar
                .replace("{{nome}}", "")
                .replace("{{email}}", "VICTOR_PIETRO@HOTMAIL.COM")
                .replace("{{dataNascimento}}", "1995-07-06");

        given()
                .body(criarCliente)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .body("mensagem", is("O CAMPO NOME NÃO PODE ESTAR VAZIO."))
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void criarErro_NomeEmBranco_Retornando400() {
        String criarCliente = clienteCriar
                .replace("{{nome}}", " ")
                .replace("{{email}}", "VICTOR_PIETRO@HOTMAIL.COM")
                .replace("{{dataNascimento}}", "1995-07-06");

        given()
                .body(criarCliente)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .body("mensagem", is("O CAMPO NOME NÃO PODE ESTAR VAZIO."))
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void criarErro_NomeMaximoInvalido_Retornando400() {
        String criarCliente = clienteCriar
                .replace("{{nome}}", "ESSE NOME O QUAL VOCÊ ESTÁ LENDO AGORA CONTÉM MAIS DO QUE 100 CARACETERES PERMITIDOS PELA REGRA DE NEGÓCIO, POR ISSO O TAMANHO EXTENSO.")
                .replace("{{email}}", "VICTOR_PIETRO@HOTMAIL.COM")
                .replace("{{dataNascimento}}", "1995-07-06");

        given()
                .body(criarCliente)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .body("mensagem", is("O CAMPO NOME NÃO DEVE CONTER MAIS DO QUE 100 CARACTERES."))
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void criarErro_EmailNulo_Retornando400() {
        String criarCliente = clienteCriar
                .replace("{{nome}}", "VICTOR PIETRO REIS")
                .replace("{{email}}", "")
                .replace("{{dataNascimento}}", "1995-07-06");

        given()
                .body(criarCliente)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .body("mensagem", is("EMAIL INVÁLIDO."))
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void criarErro_EmailEmBranco_Retornando400(){
        String criarCliente = clienteCriar
                .replace("{{nome}}", "VICTOR PIETRO REIS")
                .replace("{{email}}", " ")
                .replace("{{dataNascimento}}", "1995-07-06");

        given()
                .body(criarCliente)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .body("mensagem", is("EMAIL INVÁLIDO."))
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void criarErro_EmailPatternErrado_Retornando400(){
        String criarCliente = clienteCriar
                .replace("{{nome}}", "VICTOR PIETRO REIS")
                .replace("{{email}}", "EMAIL.COM.PATTERN.ERRADO")
                .replace("{{dataNascimento}}", "1995-07-06");

        given()
                .body(criarCliente)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .body("mensagem", is("EMAIL INVÁLIDO."))
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void criarErro_DataNula_Retornando400() {
        String criarCliente = clienteCriar
                .replace("{{nome}}", "VICTOR PIETRO REIS")
                .replace("{{email}}", "VICTOR_PIETRO@HOTMAIL.COM")
                .replace("{{dataNascimento}}", "");

        given()
                .body(criarCliente)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .body("mensagem", is("O CAMPO DATA DE NASCIMENTO É OBRIGATÓRIO."))
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}
