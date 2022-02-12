package com.stock.quotation.app.repository;

import com.stock.quotation.app.model.StockQuote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockQuoteRepository extends JpaRepository<StockQuote, Long> {
}
