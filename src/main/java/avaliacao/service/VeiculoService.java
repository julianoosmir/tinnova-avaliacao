package avaliacao.service;

import avaliacao.dto.MarcaCountDTO;
import avaliacao.dto.VeiculoInputDTO;
import avaliacao.dto.VeiculoInputSeachDTO;
import avaliacao.dto.VeiculoOutputDTO;
import avaliacao.entity.Veiculo;
import avaliacao.repository.VeiculoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    private final CotacaoDolarService cotacaoDolarService;

    public List<VeiculoOutputDTO> buscarTodos(int page, int pageSize) {
       return this.veiculoRepository.buscarTodos(page,pageSize)
               .stream()
               .map(Veiculo::toOutput)
               .toList();
    }

    public List<MarcaCountDTO> relatorioMarcas(int page, int pageSize) {
        return this.veiculoRepository.relatorioMarcas(page,pageSize);
    }

    public VeiculoOutputDTO buscarPorId(UUID id) {
        return this.veiculoRepository.findByIdOptional(id)
                .orElseThrow(() -> new EntityNotFoundException("veiculo não encontrado")).toOutput();
    }

    public List<VeiculoOutputDTO> buscarTodosPorParametros(VeiculoInputSeachDTO dto,int page, int pageSize) {
        return this.veiculoRepository.buscarPorParametros(dto,page,pageSize).stream().map(Veiculo::toOutput).toList();
    }

    @Transactional
    public void salvar(VeiculoInputDTO veiculodto) {
        Veiculo veiculo = Veiculo.fromInput(veiculodto);
        veiculo.setPreco(cotacaoDolarService.buscarCotacaoDolar(veiculo.getPreco()));
        this.veiculoRepository.persist(veiculo);
    }

    @Transactional
    public void alterar(UUID uuid, VeiculoInputDTO veiculodto) {
        Veiculo veiculo = this.veiculoRepository.findByIdOptional(uuid)
                .orElseThrow(() -> new EntityNotFoundException("veiculo não encontrado"));

        veiculo.setPreco(veiculodto.preco() != null ? cotacaoDolarService.buscarCotacaoDolar(veiculodto.preco()) : veiculo.getPreco());
        veiculo.setPlaca(veiculodto.preco() != null ? veiculodto.placa() :  veiculo.getPlaca());
        veiculo.setCor( veiculodto.preco() != null ? veiculodto.cor()  :  veiculo.getCor());
        veiculo.setAno(veiculodto.preco() != null ? veiculodto.ano() :  veiculo.getAno());
        veiculo.setMarca(veiculodto.marca() != null ? veiculodto.marca() : veiculo.getMarca());

        this.veiculoRepository.persist(veiculo);
    }


    @Transactional
    public void deletarVeiculo(UUID uuid) {
        Veiculo veiculo = this.veiculoRepository.findById(uuid);
        veiculo.setAtivo(false);
        this.veiculoRepository.persistAndFlush(veiculo);
    }
}

