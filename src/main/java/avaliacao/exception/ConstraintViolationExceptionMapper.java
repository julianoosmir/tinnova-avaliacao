package avaliacao.exception;

import avaliacao.dto.ErroResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.hibernate.exception.ConstraintViolationException;

import java.time.LocalDateTime;
import java.util.List;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException e) {
        ErroResponse erro = new ErroResponse(
                LocalDateTime.now(),
                409,
                "Conflito de dados",
                List.of("Um registro com este identificador único (ex: placa) já existe.")
        );
        return Response.status(409).entity(erro).build();
    }
}
