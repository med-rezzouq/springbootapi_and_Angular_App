package com.ecommerce.services.admin;

import com.ecommerce.dto.*;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.enums.UserRole;
import com.ecommerce.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CartRepo cartRepo;

    //Product operations

    @Override
    public Product addProduct(myProductDto myProductDto) throws IOException {
        Product product = new Product();
        product.setName(myProductDto.getName());
        product.setAvailableQuantity(myProductDto.getAvailableQuantity());
        product.setPrice(myProductDto.getPrice());
        product.setDescription(myProductDto.getDescription());
        product.setImg(myProductDto.getImg().getBytes());
        return productRepo.save(product);
    }

    @Override
    public List<myProductDto> getAllProducts() {
        List<Product> products = productRepo.findAll();
        return products.stream().map(product -> {
            myProductDto myProductDto = new myProductDto();
            myProductDto.setId(product.getId());
            myProductDto.setName(product.getName());
            myProductDto.setDescription(product.getDescription());
            myProductDto.setAvailableQuantity(product.getAvailableQuantity());
            myProductDto.setPrice(product.getPrice());
            myProductDto.setReturnedImg(product.getImg());
            return myProductDto;
        }).collect(Collectors.toList());
    }

    @Override
    public myProductDto getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            myProductDto myProductDto = new myProductDto();
            myProductDto.setId(product.getId());
            myProductDto.setName(product.getName());
            myProductDto.setDescription(product.getDescription());
            myProductDto.setAvailableQuantity(product.getAvailableQuantity());
            myProductDto.setPrice(product.getPrice());
            myProductDto.setReturnedImg(product.getImg());
            return myProductDto;
        } else {
            return null;
        }
    }

    @Override
    public myProductDto updateProduct(Long productId, myProductDto myProductDto) throws IOException {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(myProductDto.getName());
            product.setAvailableQuantity(myProductDto.getAvailableQuantity());
            product.setPrice(myProductDto.getPrice());
            product.setDescription(myProductDto.getDescription());
            if (myProductDto.getImg() != null) {
                product.setImg(myProductDto.getImg().getBytes());
            }
            Product updatedProduct = productRepo.save(product);
            myProductDto updatedProductDto = new myProductDto();
            updatedProductDto.setId(updatedProduct.getId());
            updatedProductDto.setName(updatedProduct.getName());
            updatedProductDto.setDescription(updatedProduct.getDescription());
            updatedProductDto.setAvailableQuantity(updatedProduct.getAvailableQuantity());
            updatedProductDto.setPrice(updatedProduct.getPrice());
            updatedProductDto.setReturnedImg(updatedProduct.getImg());
            return updatedProductDto;
        } else {
            return null;
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("Product with id " + productId + " not found");
        }
        productRepo.deleteById(productId);
    }

    @Override
    public List<myProductDto> searchProductByTitle(String title) {
        List<Product> products = productRepo.findAllByNameContaining(title);
        return products.stream().map(product -> {
            myProductDto myProductDto = new myProductDto();
            myProductDto.setId(product.getId());
            myProductDto.setName(product.getName());
            myProductDto.setDescription(product.getDescription());
            myProductDto.setAvailableQuantity(product.getAvailableQuantity());
            myProductDto.setPrice(product.getPrice());
            myProductDto.setReturnedImg(product.getImg());
            return myProductDto;
        }).collect(Collectors.toList());
    }

    //User operations

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAllByRole(UserRole.USER);
        return users.stream().map(user -> {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setRole(user.getRole());
            userDto.setReturnedImg(user.getImg());
            return userDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllPlacedOrders() {
        List<Order> orderList = orderRepo.findAllByStatus(OrderStatus.Submitted);
        return orderList.stream().map(order -> {
            OrderDto orderDto = new OrderDto();
            orderDto.setId(order.getId());
            orderDto.setDate(order.getDate());
            orderDto.setAmount(order.getAmount());
            orderDto.setStatus(order.getStatus());
            orderDto.setUserName(order.getUser().getName());
            return orderDto;
        }).collect(Collectors.toList());
    }


}
