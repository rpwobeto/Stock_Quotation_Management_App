package com.stock.quotation.app.controller;

import javax.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stockcachecontroller")

@Transactional
public class StockCacheController {

    @DeleteMapping
    @Transactional
    @CacheEvict(value = "stockCache", allEntries = true)
    public ResponseEntity<?> cleanStockCache() {
        System.out.println("cache was cleaned");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Stock Cache was cleaned");
    }

}