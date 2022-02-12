package com.stock.quotation.app.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StockForm {

    private String stockId;

    private Map<String, String> quotes;

    public String getStockId() {
        return stockId;
    }

    public Map<String, String> getQuotes() {
        return quotes;
    }

    public List<StockQuote> generateStockQuoteList(Stock stock) {
        List<StockQuote> quotes = new ArrayList<>();

        for (Map.Entry<String, String> entry : this.quotes.entrySet()) {
            StockQuote quote = new StockQuote();
            quote.setDate(LocalDate.parse(entry.getKey()));
            quote.setValue(new BigDecimal(entry.getValue()));
            quote.setStock(stock);
            quotes.add(quote);
        }
        return quotes;
    }

}