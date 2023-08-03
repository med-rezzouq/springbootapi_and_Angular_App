package com.ecommerce.controller;

import com.ecommerce.dto.*;
import com.ecommerce.responce.GeneralResponse;
import com.ecommerce.services.admin.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Product operations

    @GetMapping("/products")
    public ResponseEntity<List<myProductDto>> getAllProducts() {
        List<myProductDto> myProductDtos = orderService.getAllProducts();
        return ResponseEntity.ok(myProductDtos);
    }

    @PostMapping("/cart")
    public ResponseEntity<String> addProductToCart(@RequestBody CartItemsDto cartItemsDto) {
        return orderService.addProductToCart(cartItemsDto);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<OrderDto> getCartByUserId(@PathVariable Long userId) {
        OrderDto orderDto = orderService.getCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @PostMapping("/deduction")
    public ResponseEntity<OrderDto> addMinusOnProduct(@RequestBody QuantityChangeProductDto quantityChangeProductDto) {
        OrderDto orderDto = orderService.addMinusOnProduct(quantityChangeProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
    }

    @PostMapping("/addition")
    public ResponseEntity<OrderDto> addPlusOnProduct(@RequestBody QuantityChangeProductDto quantityChangeProductDto) {
        OrderDto OrderDto = orderService.addPlusOnProduct(quantityChangeProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderDto);
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
        OrderDto OrderDto = orderService.PlaceOrder(placeOrderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderDto);
    }

    @GetMapping("/myOrders/{userId}")
    public ResponseEntity<List<OrderDto>> getMyPlacedOrders(@PathVariable Long userId) {
        List<OrderDto> orderDtos = orderService.getMyPlacedOrders(userId);
        return ResponseEntity.ok(orderDtos);
    }

}
