package com.testlab2.application;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class CryptoService {

    public String getCoinPrice(String cryptoName) {
        String coincapBitcoinAPIURL = "https://api.coincap.io/v2/assets/" + cryptoName;
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<DataFromAPI> response = restTemplate.getForEntity(coincapBitcoinAPIURL, DataFromAPI.class);

        CoinData coinData = response.getBody().getData();
        return coinData.getPriceUsd();
    }
}

@Getter
@Setter
class CoinData {
    private String id;
    private String symbol;
    private String name;
    private String priceUsd;
}
@Getter
@Setter
class DataFromAPI {
    private CoinData data;
}
