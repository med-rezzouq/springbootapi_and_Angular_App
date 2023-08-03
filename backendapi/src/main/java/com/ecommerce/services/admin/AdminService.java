package com.ecommerce.services.admin;

import com.ecommerce.dto.*;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.responce.GeneralResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AdminService {

    //Product operations

    Product addProduct(myProductDto myProductDto) throws IOException;

    List<myProductDto> getAllProducts();

    myProductDto getProductById(Long productId);

    myProductDto updateProduct(Long productId, myProductDto myProductDto) throws IOException;

    void deleteProduct(Long productId);

    List<myProductDto> searchProductByTitle(String title);

    //User Operations

    List<UserDto> getAllUsers();

    //Get All Placed Orders

    List<OrderDto> getAllPlacedOrders();

}
