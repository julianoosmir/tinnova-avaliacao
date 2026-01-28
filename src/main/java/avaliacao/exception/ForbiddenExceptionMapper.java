package avaliacao.exception;

import avaliacao.dto.ErroResponse;
import jakarta.annotation.Priority;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;
import java.util.List;

@Provider
@Priority(1)
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {

    @Override
    public Response toResponse(ForbiddenException e) {
        ErroResponse erro = new ErroResponse(
                LocalDateTime.now(),
                403,
                "Acesso Negado",
                List.of("Você não tem permissão para realizar esta operação ou acessar este recurso.")
        );

        return Response.status(Response.Status.FORBIDDEN)
                .entity(erro)
                .build();
    }
}
