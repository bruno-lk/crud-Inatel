package com.example.stocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@Autowired
	private final StockRepository stockRepository;
	
	Controller(StockRepository stockRepository){
		this.stockRepository = stockRepository;
	}
	
	@PostMapping("/stock")
	public @ResponseBody Stock addNewStock(@RequestBody Stock newStock) {
		 return stockRepository.save(newStock);	
	}
	
	@PatchMapping("/stock/{name}")
	public @ResponseBody Stock updateStock(@RequestBody Stock newStock, @PathVariable String name) {
		Stock stock =  stockRepository.findByName(name);
		String newName = newStock.getName();
		Float[] newQuotes = newStock.getQuotes();
		Float[] oldQuotes = stock.getQuotes();

		if(newName != null) {
			stock.setName(newStock.getName());			
		}
		
		if(oldQuotes != null) {
			int newLen = newQuotes.length;
			int oldLen = oldQuotes.length;
			Float[] result = new Float[newLen + oldLen];
			System.arraycopy(oldQuotes, 0, result, 0, oldLen);
			System.arraycopy(newQuotes, 0, result, oldLen, newLen);
			stock.setQuotes(result);	
		}

		return stockRepository.save(stock);			
		
	}
	
	@GetMapping("/stock")
	public @ResponseBody Object getAllStocks(@RequestParam(required = false) String name) {

		if(name != null) {
			return getStock(name);
		}
		
		return stockRepository.findAll();
	}
	
	@GetMapping("/stock/{name}")
	public @ResponseBody Stock getStock(@PathVariable String name) {
			
		return stockRepository.findByName(name);
	}
}
