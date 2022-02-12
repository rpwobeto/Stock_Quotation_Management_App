package com.stock.quotation.app.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/stockquotes")
public class StockQuoteController {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private StockQuoteRepository stockQuoteRepository;
    @Autowired
    private StockService stockService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createStockQuote(@RequestBody StockForm form, UriComponentsBuilder uriBuilder) {

        List<StockRegister> stockList = stockService.listStocks();
        List<String> stockIdList = stockList.stream().map(StockRegister::getId).collect(Collectors.toList());

        if (!stockIdList.contains(form.getStockId())) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Post not allowed. This stock id: " + form.getStockId() + " is not registered" );
        }

        Stock stock = new Stock(form.getStockId());
        stock.setQuotes(form.generateStockQuoteList(stock));
        stockRepository.save(stock);
        URI uri = uriBuilder.path("/stocks/{id}").buildAndExpand(form.getStockId()).toUri();

        return ResponseEntity.created(uri).body(new StockDto(stock));
    }

    @GetMapping()
    public ResponseEntity<?> listAll() {
        List<Stock> stockList = stockRepository.findAll();
        if (stockList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no stocks registered");
        }
        return ResponseEntity.ok(stockList.stream().map(StockDto::new).collect(Collectors.toList()));
    }

    @GetMapping("/{stockId}")
    public ResponseEntity<?> getByStockId(@PathVariable("stockId") String stockId) {
        List<Stock> stockListByStockId = stockRepository.findByStockId(stockId);
        if (stockListByStockId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This stock id: " + stockId + " is not registered");
        }
        return ResponseEntity.ok(stockListByStockId.stream().map(StockDto::new).collect(Collectors.toList()));
    }

}