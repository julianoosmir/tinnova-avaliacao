package avaliacao.controller;

import avaliacao.dto.VeiculoInputDTO;
import avaliacao.dto.VeiculoInputSeachDTO;
import avaliacao.service.VeiculoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/veiculos")
public class VeiculoController {

    @Inject
    VeiculoService veiculoService;

    @GET
    public Response getVeiculosParams(@QueryParam("page") @DefaultValue("0") int page,
                                      @QueryParam("pageSize") @DefaultValue("10") int pageSize,
                                       @BeanParam VeiculoInputSeachDTO dto){
        return  Response.ok(this.veiculoService.buscarTodosPorParametros(dto,page,pageSize)).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarVeiculoPorId(@PathParam("id") String id){
        return Response.ok(this.veiculoService.buscarPorId(UUID.fromString(id))).build();
    }

    @POST
    public Response salvarVeiculo(VeiculoInputDTO veiculoInputDTO){
        this.veiculoService.salvar(veiculoInputDTO);
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}")
    public Response alterarVeiculoPorid(@PathParam("id") String id,VeiculoInputDTO veiculoInputDTO){
        this.veiculoService.alterar(UUID.fromString(id),veiculoInputDTO);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response alterarVeiculo(@PathParam("id") String id,VeiculoInputDTO veiculoInputDTO){
        this.veiculoService.alterar(UUID.fromString(id),veiculoInputDTO);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletarVeiculoPorId(@PathParam("id") String id){
        this.veiculoService.deletarVeiculo(UUID.fromString(id));
        return  Response.ok().build();
    }
}
