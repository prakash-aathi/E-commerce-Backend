package com.ecommerce.inventory;

import com.ecommerce.inventory.model.Inventorymodel;
import com.ecommerce.inventory.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
			Inventorymodel inventory = new Inventorymodel();
			inventory.setSkucode("iphone_13");
			inventory.setQuantity(100);

			Inventorymodel inventory1 = new Inventorymodel();
			inventory1.setSkucode("iphone_12");
			inventory1.setQuantity(0);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};
	}
}