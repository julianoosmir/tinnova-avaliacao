package avaliacao.repository;

import avaliacao.dto.MarcaCountDTO;
import avaliacao.dto.VeiculoInputSeachDTO;
import avaliacao.entity.Veiculo;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class VeiculoRepository implements PanacheRepositoryBase<Veiculo, UUID> {

    public List<Veiculo> buscarTodos(int pageIndex, int pageSize) {
        return find("from Veiculo").page(Page.of(pageIndex,pageSize)).list();
    }

    public List<MarcaCountDTO> relatorioMarcas(int pageIndex, int pageSize) {
        return Veiculo.find(
                "select v.marca, count(v)" +
                        "from Veiculo v " +
                        "group by v.marca"
        ).page(Page.of(pageIndex,pageSize)).project(MarcaCountDTO.class).list();
    }

    public List<Veiculo> buscarPorParametros(VeiculoInputSeachDTO dto,int page, int pageSize) {
        StringBuilder query = new StringBuilder("ativo = true");
        Parameters params = new Parameters();

        if (dto.marca() != null && !dto.marca().isEmpty()) {
            query.append(" and marca like :marca");
            params.and("marca", "%" + dto.marca() + "%");
        }

        if (dto.ano() != null && !dto.ano().isEmpty()) {
            query.append(" and ano = :ano");
            params.and("ano", dto.ano());
        }

        if (dto.cor() != null && !dto.cor().isEmpty()) {
            query.append(" and cor like :cor");
            params.and("cor", "%" + dto.cor() + "%");
        }

        if (dto.valorMinimo() != null) {
            query.append(" and preco >= :valorMinimo");
            params.and("valorMinimo", dto.valorMinimo());
        }

        if (dto.valorMaximo() != null) {
            query.append(" and preco <= :valorMaximo");
            params.and("valorMaximo", dto.valorMaximo());
        }

        PanacheQuery<Veiculo> veiculosQuery = find(query.toString(), params);
        veiculosQuery.page(Page.of(page,pageSize));

        return veiculosQuery.stream().toList();
    }


}
