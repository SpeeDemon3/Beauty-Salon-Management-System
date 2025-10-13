package com.ruiz.Beauty.Salon.Management.System.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal salePrice;
    private BigDecimal costPrice;
    private Integer currentStock;
    private Integer minimumStock;
}
