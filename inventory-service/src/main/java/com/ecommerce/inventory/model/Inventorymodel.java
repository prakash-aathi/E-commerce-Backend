package com.ecommerce.inventory.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table
@Data @AllArgsConstructor @NoArgsConstructor 
public class Inventorymodel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skucode;
    private Integer quantity;

}
