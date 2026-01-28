package avaliacao.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.math.RoundingMode;

@ApplicationScoped
public class CotacaoDolarService {

    @RestClient
    @Inject
    AwesomeApiService awesomeapiService;

    @RestClient
    @Inject
    FrankfurterApi frankfurterApi;


    @Fallback(fallbackMethod = "buscarCotacaoDolarFallBack")
    public BigDecimal buscarCotacaoDolar(BigDecimal valorReal) {
        return valorReal.divide(awesomeapiService.getUltimaCotacaoDolarParaReal()
                .get("USDBRL").ask(),2, RoundingMode.HALF_UP);
    }

    public BigDecimal buscarCotacaoDolarFallBack(BigDecimal valorReal) {
        return frankfurterApi.getFrankFurter(valorReal).rates().get("USD");
    }

}
