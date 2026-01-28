package avaliacao.controller;

import avaliacao.dto.JwtRequest;
import avaliacao.service.AuthenticationService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/auth")
public class AuthController {

    @Inject
    AuthenticationService authenticationService;

    @POST
    public String autenticar(JwtRequest jwtRequest) {
        return authenticationService.autenticar(jwtRequest.username(), jwtRequest.senha());
    }

    @GET
    @Path("/teste")
    @RolesAllowed({"ADMIN"})
    public String autenticado() {
        return "Hello from Quarkus REST";
    }

}