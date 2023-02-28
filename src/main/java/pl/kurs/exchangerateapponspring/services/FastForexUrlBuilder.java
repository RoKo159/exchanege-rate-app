package pl.kurs.exchangerateapponspring.services;

import org.springframework.stereotype.Service;
import pl.kurs.exchangerateapponspring.config.AppConfig;


@Service
public class FastForexUrlBuilder implements IUrlBuilder {


    @Override
    public String build(String currencyFrom) {
        return AppConfig.API_PAGE + AppConfig.ENDPOINT_WITH_PARAMETER + currencyFrom + AppConfig.PRIVATE_API_KEY;
    }
}
