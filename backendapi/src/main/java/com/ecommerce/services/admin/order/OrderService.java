package com.ecommerce.services.admin.order;

import com.ecommerce.dto.*;
import com.ecommerce.responce.GeneralResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {

    List<myProductDto> getAllProducts();

    ResponseEntity<String> addProductToCart(CartItemsDto cartItemsDto);

    OrderDto getCartByUserId(Long userId);

    OrderDto addMinusOnProduct(QuantityChangeProductDto quantityChangeProductDto);

    OrderDto addPlusOnProduct(QuantityChangeProductDto quantityChangeProductDto);

    OrderDto PlaceOrder(PlaceOrderDto placeOrderDto);

    List<OrderDto> getMyPlacedOrders(Long userId);
}
