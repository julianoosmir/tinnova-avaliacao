package avaliacao.dto;

import org.jboss.resteasy.reactive.RestQuery;

import java.math.BigDecimal;

public record VeiculoInputSeachDTO(@RestQuery String marca,
                                   @RestQuery String ano,
                                   @RestQuery String cor,
                                   @RestQuery BigDecimal valorMaximo,
                                   @RestQuery BigDecimal valorMinimo) {
}
