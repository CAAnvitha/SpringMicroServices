package com.anvitha.inventoryservice.controller;

import com.anvitha.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.ls.LSException;
import com.anvitha.inventoryservice.dto.InventoryResponse;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
//http://Localhost:8082/api/inventory/iphone-15,iphone13-red
// http://Localhost:8082/api/inventory?sku-code=iphone-13&skU-code=iphone13-red

//    @GetMapping("/{sku-code}")
//    @ResponseStatus(HttpStatus.OK)
//    public boolean inStock(@PathVariable("sku-code") String skuCode){
//        return inventoryService.isInStock(skuCode);
//
//    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> inStock(@RequestParam("skuCode") List<String> skuCode){
//        return "Inventory Sucess"
        return inventoryService.isInStock(skuCode);

    }
}
