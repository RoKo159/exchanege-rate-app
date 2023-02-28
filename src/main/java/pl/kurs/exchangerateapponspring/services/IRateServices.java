package pl.kurs.exchangerateapponspring.services;

import pl.kurs.exchangerateapponspring.exceptions.InvalidInputDataException;
import pl.kurs.exchangerateapponspring.exceptions.NoConnectionException;

import java.math.BigDecimal;

public interface IRateServices {

    BigDecimal getRate(String fromCurrencyMark, String toCurrencyMark) throws InvalidInputDataException, NoConnectionException;



}
