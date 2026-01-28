package avaliacao.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record VeiculoOutputDTO(
    UUID id,
    String marca,
    String ano,
    String cor,
    BigDecimal preco,
    String placa
) {}
