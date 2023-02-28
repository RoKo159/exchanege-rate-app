package pl.kurs.exchangerateapponspring.services;

import org.springframework.stereotype.Service;
import pl.kurs.exchangerateapponspring.exceptions.InvalidInputDataException;
import pl.kurs.exchangerateapponspring.exceptions.NoConnectionException;


import java.math.BigDecimal;


@Service
public class CurrencyService implements ICurrencyService {

    private IRateServices rateServices;


    public CurrencyService(IRateServices rateServices) {
        this.rateServices = rateServices;
    }

    @Override
    public BigDecimal exchange(String currencyFrom, String currencyTo, BigDecimal amount) throws InvalidInputDataException, NoConnectionException {
        if (amount.doubleValue() <= 0)
            throw new InvalidInputDataException("Wartość musi być większa od zera!");
        BigDecimal rate = rateServices.getRate(currencyFrom, currencyTo);
        BigDecimal exchangeResult = rate.multiply(amount);
        return exchangeResult;
    }
}
