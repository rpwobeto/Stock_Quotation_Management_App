package com.stock.quotation.management.stock;

import java.util.ArrayList;

import com.stock.quotation.app.service.StockRegister;
import com.stock.quotation.app.service.StockService;
import org.aspectj.lang.annotation.Before;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StockService stockService;

    private List<StockRegister> stockList;

    private JSONObject quotes;
    private JSONObject body;

    @Before
    void populateMockStockList() {
        Mockito.when(stockService.listStocks()).thenReturn(stockList);

        this.stockList = new ArrayList<>();
        this.stockList.add(new StockRegister("petr3", "Petrobras3"));
        this.stockList.add(new StockRegister("petr4", "Petrobras4"));
    }

    @Test
    void shouldGenerateNewOperation() throws Exception {

        populateMockStockList();
        quotes = new JSONObject();
        quotes.put("2022-10-10", "10");
        quotes.put("2022-11-10", "20");
        quotes.put("2022-12-10", "30");

        body = new JSONObject();
        body.put("stockId", "petr3");
        body.put("quotes", quotes);

        mockMvc.perform(MockMvcRequestBuilders.post("/quote").content(body.toString()).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(201));

        body = new JSONObject();
        body.put("stockId", "testing");
        body.put("quotes", quotes);

        mockMvc.perform(MockMvcRequestBuilders.post("/quote").content(body.toString()).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(502));
    }

    @Test
    void listsAllStockQuotes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/quote").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void listsStockQuotesByStockId() throws Exception {

        populateMockStockList();

        String validStockId = "petr3";
        String invalidStockId = "Id01";

        if(stockList.toString().contains(validStockId)) {
            mockMvc.perform(MockMvcRequestBuilders.get("/quote/"+validStockId).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(200));
        }

        if(!stockList.toString().contains(invalidStockId)) {
            mockMvc.perform(MockMvcRequestBuilders.get("/quote/"+invalidStockId).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(404));
        }
    }

}
