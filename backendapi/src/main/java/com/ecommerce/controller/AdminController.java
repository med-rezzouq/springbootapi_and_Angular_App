package com.ecommerce.controller;

import com.ecommerce.dto.*;
import com.ecommerce.entity.Product;
import com.ecommerce.services.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    //Product operations

    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@ModelAttribute myProductDto myProductDto) throws IOException {
        Product product = adminService.addProduct(myProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping("/products")
    public ResponseEntity<List<myProductDto>> getAllProducts() {
        List<myProductDto> myProductDtos = adminService.getAllProducts();
        return ResponseEntity.ok(myProductDtos);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<myProductDto> getProductById(@PathVariable Long productId) {
        myProductDto myProductDto = adminService.getProductById(productId);
        if (myProductDto != null) {
            return ResponseEntity.ok(myProductDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<myProductDto> updateProduct(@PathVariable Long productId, @ModelAttribute myProductDto myProductDto) throws IOException {
        myProductDto updatedProduct = adminService.updateProduct(productId, myProductDto);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        adminService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<List<myProductDto>> searchProductByTitle(@PathVariable("title") String title) {
        List<myProductDto> myProductDtos = adminService.searchProductByTitle(title);
        return ResponseEntity.ok(myProductDtos);
    }

    //User Operations

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtoList = adminService.getAllUsers();
        return ResponseEntity.ok(userDtoList);
    }

    //Get All Placed Orders

    @GetMapping("/placedOrders")
    public ResponseEntity<List<OrderDto>> getAllPlacedOrders() {
        List<OrderDto> allPlacedOrdersDtos = adminService.getAllPlacedOrders();
        return ResponseEntity.ok(allPlacedOrdersDtos);
    }

}
