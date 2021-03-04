package com.example.stocks;

import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock, Integer>{
	
	Stock findByName(String name);
}
