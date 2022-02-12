package com.stock.quotation.app.service;

import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockService {

    private RestTemplate restTemplate = new RestTemplate();
    private String stockManagerURL;

    @Autowired
    public StockService(@Value("${stock-manager.url}") String stockManagerURL) {
        this.stockManagerURL = stockManagerURL;
    }

    @Cacheable(value = "stockCache")
    public List<StockRegister> listStocks() {
        System.out.println("cache connected");
        StockRegister[] stockList = restTemplate.getForObject(stockManagerURL + "/stock", StockRegister[].class);
        return Arrays.asList(stockList);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void registerApplicationInStockManager() {
        JSONObject body = new JSONObject();
        body.put("host", "localhost");
        body.put("port", 8081);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<String>(body.toString(), header);
        restTemplate.postForObject(stockManagerURL + "/notification", request, String.class);
        System.out.println("Stock Quotation Management App was registered");
    }

}