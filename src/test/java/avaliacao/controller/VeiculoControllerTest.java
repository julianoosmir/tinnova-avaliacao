package avaliacao.controller;

import avaliacao.DatabaseDockerInicializer;
import avaliacao.dto.VeiculoInputDTO;
import io.quarkus.test.common.WithTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusTest
@WithTestResource(DatabaseDockerInicializer.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VeiculoControllerTest {

    @Test
    @Order(1)
    @TestSecurity(user = "usuario", roles = {"USER"})
    public void getVeiculosParamsTest() {
        given()
                .get("/veiculos")
                .then()
                .statusCode(200)
                .body("data.size()", Matchers.is(2))
                .log().all();

        given().params("cor","branco")
                .get("/veiculos")
                .then()
                .statusCode(200)
                .body("data.size()", Matchers.is(1))
                .log().all();
    }

    @Test
    @Order(1)
    @TestSecurity(user = "usuario", roles = {"USER"})
    public void getVeiculosByIdTest() {
        given()
                .get("/veiculos/" + UUID.fromString("d218fba8-26d6-436f-a0b3-6067f78a8ec6"))
                .then()
                .statusCode(200)
                .body("ano", Matchers.is("2013"))
                .body("cor", Matchers.is("preto"))
                .body("marca", Matchers.is("gol"))
                .body("placa", Matchers.is("zap34"))
                .log().all();
    }

    @Test
    @Order(1)
    @TestSecurity(user = "usuario", roles = {"USER"})
    public void getVeiculosRelatorioTest() {
        given()
                .get("/veiculos/relatorios/por-marca")
                .then()
                .statusCode(200)
                .body("data.size()", Matchers.is(1))
                .log().all();
    }

    @Test
    @Order(2)
    public void getVeiculosParamsUnauthorizedExceptionTest() {
        given()
                .get("/veiculos")
                .then()
                .statusCode(401)
                .body("detalhes[0]",Matchers.is("Usuario sem autorização"))
                .log().all();
    }

    @Test
    @Order(3)
    @TestSecurity(user = "usuario", roles = {"TEST"})
    public void getVeiculosParamsForbiddenExceptionTest() {
        given()
                .get("/veiculos")
                .then()
                .statusCode(403)
                .body("detalhes[0]",Matchers.is("Você não tem permissão para realizar esta operação ou acessar este recurso."))
                .body("mensagem",Matchers.is("Acesso Negado"))
                .log().all();
    }

    @Test
    @Order(4)
    @TestSecurity(user = "usuario", roles = {"ADMIN"})
    public void salvarVeiculoTest() {
        var body = new VeiculoInputDTO("gol","2013","preto", BigDecimal.valueOf(100L),"TS34");

        given().contentType(ContentType.JSON)
                .when()
                .log().all()
                .body(body)
                .post("/veiculos")
                .then()
                .statusCode(200)
                .log().all();
    }


    @Test
    @Order(5)
    @TestSecurity(user = "usuario", roles = {"USER"})
    public void salvarVeiculoForbiddenExceptionTest() {
        var body = new VeiculoInputDTO("gol","2013","preto", BigDecimal.valueOf(100L),"teste");

        given().contentType(ContentType.JSON)
                .when()
                .log().all()
                .body(body)
                .post("/veiculos")
                .then()
                .statusCode(403)
                .body("detalhes[0]",Matchers.is("Você não tem permissão para realizar esta operação ou acessar este recurso."))
                .body("mensagem",Matchers.is("Acesso Negado"))
                .log().all();
    }

    @Test
    @Order(6)
    public void salvarVeiculoUnauthorizedExceptionTest() {
        var body = new VeiculoInputDTO("gol","2013","preto", BigDecimal.valueOf(100L),"teste");

        given().contentType(ContentType.JSON)
                .when()
                .log().all()
                .body(body)
                .post("/veiculos")
                .then()
                .statusCode(401)
                .body("detalhes[0]",Matchers.is("Usuario sem autorização"))
                .log().all();
    }

    @Test
    @Order(7)
    @TestSecurity(user = "usuario", roles = {"ADMIN"})
    public void salvarVeiculoConstraintViolationExceptionTest() {
        var body = new VeiculoInputDTO("gol","2013","preto", BigDecimal.valueOf(100L),"zap34");

        given().contentType(ContentType.JSON)
                .when()
                .log().all()
                .body(body)
                .post("/veiculos")
                .then()
                .statusCode(409)
                .body("detalhes[0]",Matchers.is("Um registro com este identificador único (ex: placa) já existe."))
                .body("mensagem",Matchers.is("Conflito de dados"))
                .log().all();
    }


    @Test
    @Order(8)
    @TestSecurity(user = "usuario", roles = {"ADMIN"})
    public void alterarVeiculoTest() {
        var body = new VeiculoInputDTO("gol","2013","preto", BigDecimal.valueOf(100L),"patch");

        given().contentType(ContentType.JSON)
                .when()
                .log().all()
                .body(body)
                .patch("/veiculos/" + UUID.fromString("d218fba8-26d6-436f-a0b3-6067f78a8ec6").toString())
                .then()
                .statusCode(200)
                .log().all();
    }


    @Test
    @Order(9)
    @TestSecurity(user = "usuario", roles = {"USER"})
    public void alterarVeiculoForbiddenExceptionTest() {
        var body = new VeiculoInputDTO("gol","2013","preto", BigDecimal.valueOf(100L),"patch");

        given().contentType(ContentType.JSON)
                .when()
                .log().all()
                .body(body)
                .patch("/veiculos/" + UUID.fromString("d218fba8-26d6-436f-a0b3-6067f78a8ec6").toString())
                .then()
                .statusCode(403)
                .body("detalhes[0]",Matchers.is("Você não tem permissão para realizar esta operação ou acessar este recurso."))
                .body("mensagem",Matchers.is("Acesso Negado"))
                .log().all();
    }

    @Test
    @Order(10)
    public void alterarVeiculoUnauthorizedExceptionTest() {
        var body = new VeiculoInputDTO("gol","2013","preto", BigDecimal.valueOf(100L),"teste");

        given().contentType(ContentType.JSON)
                .when()
                .log().all()
                .body(body)
                .patch("/veiculos/" + UUID.fromString("d218fba8-26d6-436f-a0b3-6067f78a8ec6").toString())
                .then()
                .statusCode(401)
                .body("detalhes[0]",Matchers.is("Usuario sem autorização"))
                .log().all();
    }

    @Test
    @Order(11)
    @TestSecurity(user = "usuario", roles = {"ADMIN"})
    public void alterarVeiculoConstraintViolationExceptionTest() {
        var body = new VeiculoInputDTO("gol","2013","preto", BigDecimal.valueOf(100L),"zap35");

        given().contentType(ContentType.JSON)
                .when()
                .log().all()
                .body(body)
                .patch("/veiculos/" + UUID.fromString("d218fba8-26d6-436f-a0b3-6067f78a8ec6").toString())
                .then()
                .statusCode(409)
                .body("detalhes[0]",Matchers.is("Um registro com este identificador único (ex: placa) já existe."))
                .body("mensagem",Matchers.is("Conflito de dados"))
                .log().all();
    }


    @Test
    @Order(12)
    @TestSecurity(user = "usuario", roles = {"ADMIN"})
    public void alteraracaoVeiculoTest() {
        var body = new VeiculoInputDTO("gol","2013","preto", BigDecimal.valueOf(100L),"putch");

        given().contentType(ContentType.JSON)
                .when()
                .log().all()
                .body(body)
                .put("/veiculos/" + UUID.fromString("d218fba8-26d6-436f-a0b3-6067f78a8ec6").toString())
                .then()
                .statusCode(200)
                .log().all();
    }


    @Test
    @Order(13)
    @TestSecurity(user = "usuario", roles = {"USER"})
    public void alteraracaoVeiculoForbiddenExceptionTest() {
        var body = new VeiculoInputDTO("gol","2013","preto", BigDecimal.valueOf(100L),"patch");

        given().contentType(ContentType.JSON)
                .when()
                .log().all()
                .body(body)
                .put("/veiculos/" + UUID.fromString("d218fba8-26d6-436f-a0b3-6067f78a8ec6").toString())
                .then()
                .statusCode(403)
                .body("detalhes[0]",Matchers.is("Você não tem permissão para realizar esta operação ou acessar este recurso."))
                .body("mensagem",Matchers.is("Acesso Negado"))
                .log().all();
    }

    @Test
    @Order(14)
    public void alteraracaoVeiculoUnauthorizedExceptionTest() {
        var body = new VeiculoInputDTO("gol","2013","preto", BigDecimal.valueOf(100L),"teste");

        given().contentType(ContentType.JSON)
                .when()
                .log().all()
                .body(body)
                .put("/veiculos/" + UUID.fromString("d218fba8-26d6-436f-a0b3-6067f78a8ec6").toString())
                .then()
                .statusCode(401)
                .body("detalhes[0]",Matchers.is("Usuario sem autorização"))
                .log().all();
    }

    @Test
    @Order(15)
    @TestSecurity(user = "usuario", roles = {"ADMIN"})
    public void alteraracaoVeiculoConstraintViolationExceptionTest() {
        var body = new VeiculoInputDTO("gol","2013","preto", BigDecimal.valueOf(100L),"zap35");

        given().contentType(ContentType.JSON)
                .when()
                .log().all()
                .body(body)
                .put("/veiculos/" + UUID.fromString("d218fba8-26d6-436f-a0b3-6067f78a8ec6").toString())
                .then()
                .statusCode(409)
                .body("detalhes[0]",Matchers.is("Um registro com este identificador único (ex: placa) já existe."))
                .body("mensagem",Matchers.is("Conflito de dados"))
                .log().all();
    }

    @Test
    @Order(16)
    @TestSecurity(user = "usuario", roles = {"ADMIN"})
    public void deletarVeiculoTest() {
        var body = new VeiculoInputDTO("gol","2013","preto", BigDecimal.valueOf(100L),"patch");

        given().contentType(ContentType.JSON)
                .when()
                .log().all()
                .body(body)
                .put("/veiculos/" + UUID.fromString("d218fba8-26d6-436f-a0b3-6067f78a8ec6").toString())
                .then()
                .statusCode(200)
                .log().all();
    }


    @Test
    @Order(17)
    @TestSecurity(user = "usuario", roles = {"USER"})
    public void deletarVeiculoForbiddenExceptionTest() {
        var body = new VeiculoInputDTO("gol","2013","preto", BigDecimal.valueOf(100L),"patch");

        given().contentType(ContentType.JSON)
                .when()
                .log().all()
                .body(body)
                .put("/veiculos/" + UUID.fromString("d218fba8-26d6-436f-a0b3-6067f78a8ec6").toString())
                .then()
                .statusCode(403)
                .body("detalhes[0]",Matchers.is("Você não tem permissão para realizar esta operação ou acessar este recurso."))
                .body("mensagem",Matchers.is("Acesso Negado"))
                .log().all();
    }

    @Test
    @Order(18)
    public void deletarVeiculoUnauthorizedExceptionTest() {
        var body = new VeiculoInputDTO("gol","2013","preto", BigDecimal.valueOf(100L),"patch");

        given().contentType(ContentType.JSON)
                .when()
                .log().all()
                .body(body)
                .put("/veiculos/" + UUID.fromString("d218fba8-26d6-436f-a0b3-6067f78a8ec6").toString())
                .then()
                .statusCode(401)
                .body("detalhes[0]",Matchers.is("Usuario sem autorização"))
                .log().all();
    }

}
