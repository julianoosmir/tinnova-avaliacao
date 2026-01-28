package avaliacao.exception;

import avaliacao.dto.ErroResponse;
import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;
import java.util.List;

@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {
    @Override
    public Response toResponse(UnauthorizedException e) {
        ErroResponse erro = new ErroResponse(
                LocalDateTime.now(),
                401,
                "Usuario sem autorização",
                List.of("Usuario sem autorização")
        );
        return Response.status(401).entity(erro).build();
    }
}
