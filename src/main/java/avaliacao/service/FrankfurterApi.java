package avaliacao.service;

import avaliacao.dto.CotacaoDolarFrankfurter;
import io.quarkus.cache.CacheResult;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.math.BigDecimal;


@RegisterRestClient
public interface FrankfurterApi {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @CacheResult(cacheName = "cotacao-cache-fallback")
    CotacaoDolarFrankfurter getFrankFurter(@QueryParam("amount") BigDecimal amount);
}
