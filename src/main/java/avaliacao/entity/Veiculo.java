package avaliacao.entity;

import avaliacao.dto.VeiculoInputDTO;
import avaliacao.dto.VeiculoOutputDTO;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.ws.rs.DefaultValue;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Veiculo extends PanacheEntityBase {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;
    private String marca;
    private String ano;
    private String cor;
    private BigDecimal preco;
    @DefaultValue("true")
    private Boolean ativo;
    @Column(unique = true)
    private String placa;

    public static Veiculo fromInput(VeiculoInputDTO input) {
        Veiculo veiculo = new Veiculo();
        veiculo.setMarca(input.marca());
        veiculo.setAno(input.ano());
        veiculo.setCor(input.cor());
        veiculo.setPreco(input.preco());
        veiculo.setPlaca(input.placa());
        veiculo.setAtivo(true);
        return veiculo;
    }

    public VeiculoOutputDTO toOutput() {
        return new VeiculoOutputDTO(
                this.id,
                this.marca,
                this.ano,
                this.cor,
                this.preco,
                this.placa
        );
    }
}
