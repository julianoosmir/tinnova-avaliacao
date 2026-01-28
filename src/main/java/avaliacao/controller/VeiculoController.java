package avaliacao.controller;

import avaliacao.dto.VeiculoInputDTO;
import avaliacao.dto.VeiculoInputSeachDTO;
import avaliacao.service.VeiculoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/veiculos")
public class VeiculoController {

    @Inject
    VeiculoService veiculoService;

    @GET
    @RolesAllowed({"ADMIN","USER"})
    public Response getVeiculosParams(@QueryParam("page") @DefaultValue("0") int page,
                                      @QueryParam("pageSize") @DefaultValue("10") int pageSize,
                                       @BeanParam VeiculoInputSeachDTO dto){
        return  Response.ok(this.veiculoService.buscarTodosPorParametros(dto,page,pageSize)).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN","USER"})
    public Response buscarVeiculoPorId(@PathParam("id") String id){
        return Response.ok(this.veiculoService.buscarPorId(UUID.fromString(id))).build();
    }

    @GET
    @Path("/relatorios/por-marca")
    @RolesAllowed({"ADMIN","USER"})
    public Response relatoriosPorMarca(@QueryParam("page") @DefaultValue("0") int page,
                                       @QueryParam("pageSize") @DefaultValue("10") int pageSize){
        return Response.ok(this.veiculoService.relatorioMarcas(page,pageSize)).build();
    }

    @POST
    @RolesAllowed({"ADMIN"})
    public Response salvarVeiculo(VeiculoInputDTO veiculoInputDTO){
        this.veiculoService.salvar(veiculoInputDTO);
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}")
    @RolesAllowed({"ADMIN"})
    public Response alterarVeiculoPorid(@PathParam("id") String id,VeiculoInputDTO veiculoInputDTO)  {
        this.veiculoService.alterar(UUID.fromString(id),veiculoInputDTO);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADMIN"})
    public Response alterarVeiculo(@PathParam("id") String id,VeiculoInputDTO veiculoInputDTO) {
        this.veiculoService.alterar(UUID.fromString(id),veiculoInputDTO);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADMIN"})
    public Response deletarVeiculoPorId(@PathParam("id") String id){
        this.veiculoService.deletarVeiculo(UUID.fromString(id));
        return  Response.ok().build();
    }
}
