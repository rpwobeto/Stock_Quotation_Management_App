package com.stock.quotation.app.repository;

import com.stock.quotation.app.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockRepository extends JpaRepository<Stock, String> {
    List<Stock> findByStockId(String stockId);
}