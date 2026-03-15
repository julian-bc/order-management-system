package com.github.julianbc.inventory_service;

import com.github.julianbc.inventory_service.model.Inventory;
import com.github.julianbc.inventory_service.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository repository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setCodeSku("iphone_12");
			inventory.setQuantity(100);

			Inventory inventory2 = new Inventory();
			inventory2.setCodeSku("iphone_12_blue");
			inventory2.setQuantity(0);

			repository.save(inventory);
			repository.save(inventory2);
		};
	}
}
