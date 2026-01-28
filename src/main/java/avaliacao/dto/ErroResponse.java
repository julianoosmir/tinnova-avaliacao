package avaliacao.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponse(LocalDateTime timestamp,
                           int status,
                           String mensagem,
                           List<String> detalhes) {
}
