package avaliacao.service;
import avaliacao.dto.CotacaoDolarAwesomeApi;
import io.quarkus.cache.CacheResult;
import jakarta.ws.rs.GET;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


import java.util.Map;

@RegisterRestClient
public interface AwesomeApiService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @CacheResult(cacheName = "cotacao-cache")
    Map<String, CotacaoDolarAwesomeApi> getUltimaCotacaoDolarParaReal();
}
