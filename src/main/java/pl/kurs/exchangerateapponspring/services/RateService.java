package pl.kurs.exchangerateapponspring.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pl.kurs.exchangerateapponspring.exceptions.InvalidInputDataException;
import pl.kurs.exchangerateapponspring.exceptions.NoConnectionException;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Optional;

@Service
public class RateService implements IRateServices {


    private IUrlBuilder urlBuilder;
    private ObjectMapper objectMapper;

    public RateService(IUrlBuilder urlBuilder, ObjectMapper objectMapper) {
        this.urlBuilder = urlBuilder;
        this.objectMapper = objectMapper;
    }

//    public static boolean isInternetAvailable() throws NoConnectionException {
//        try {
//            final URL url = new URL("http://www.google.com");
//            final URLConnection conn = url.openConnection();
//            conn.connect();
//            conn.getInputStream().close();
//            return true;
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            return false;
//        }
//    }

    @Override
    public BigDecimal getRate(String fromCurrencyMark, String toCurrencyMark) throws InvalidInputDataException, NoConnectionException {

        String preparedURL = urlBuilder.build(fromCurrencyMark);
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(new URL(preparedURL));
        } catch (MalformedURLException | UnknownHostException e) {
            throw new NoConnectionException("Problemy z połączeniem!", e);
        } catch (IOException e) {
            throw new InvalidInputDataException("Przekazano błędne dane!", e);
        }
        BigDecimal specificRate = new BigDecimal(
                Optional.ofNullable(
                                jsonNode.get("results").get(toCurrencyMark))
                        .orElseThrow(() -> new InvalidInputDataException("Przekazano błędne dane!")
                        ).asText());

        return specificRate;
    }
}
