package com.ecommerce.inventory.services;


import com.ecommerce.inventory.dto.InventoryResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.inventory.repository.InventoryRepository;

import java.util.List;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @SneakyThrows
    @Transactional( readOnly = true )
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        log.info("wait started: simulating slow response");
        Thread.sleep(10000);
        log.info("wait ended");
        return inventoryRepository.findBySkucodeIn(skuCode).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkucode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                        ).toList();
    }
    
    
    
}
