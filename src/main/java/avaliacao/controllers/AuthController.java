package avaliacao.controllers;

import avaliacao.dto.JwtRequest;
import avaliacao.service.AuthenticationService;
import jakarta.inject.Inject;

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
}