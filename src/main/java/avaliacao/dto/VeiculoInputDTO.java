package avaliacao.dto;

import java.math.BigDecimal;

public record VeiculoInputDTO(
    String marca,
    String ano,
    String cor,
    BigDecimal preco,
    String placa
) {}
