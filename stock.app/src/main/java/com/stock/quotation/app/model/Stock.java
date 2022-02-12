package com.stock.quotation.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Stock {

    @Id
    private String id;
    private String stockId;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    private List<StockQuote> quotes = new ArrayList<>();

    public Stock() {
        this.id = UUID.randomUUID().toString();
    }

    public Stock(String stockId) {
        this.id = UUID.randomUUID().toString();
        this.stockId = stockId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public List<StockQuote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<StockQuote> quotes) {
        this.quotes = quotes;
    }

}