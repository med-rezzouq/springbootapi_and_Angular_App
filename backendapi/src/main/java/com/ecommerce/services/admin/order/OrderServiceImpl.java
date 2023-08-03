package com.ecommerce.services.admin.order;

import com.ecommerce.dto.*;
import com.ecommerce.entity.*;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.repo.*;
import com.ecommerce.responce.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartRepo cartRepo;

    @Override
    public List<myProductDto> getAllProducts() {
        return productRepo.findAll().stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> addProductToCart(CartItemsDto cartItemsDto) {
        Order pendingOrder = orderRepo.findByUserIdAndStatus(cartItemsDto.getUserId(), OrderStatus.Pending);
        Optional<CartItems> cartItem = cartRepo.findByProductIdAndOrderIdAndUserId(cartItemsDto.getProductId(), pendingOrder.getId(), cartItemsDto.getUserId());
        if (cartItem.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Product already exists in the cart.");
        } else {
            Product product = null;
            Optional<Product> optionalProduct = productRepo.findById(cartItemsDto.getProductId());
            Optional<User> optionalUser = userRepo.findById(cartItemsDto.getUserId());
            Order runningOrder = orderRepo.findByUserIdAndStatus(cartItemsDto.getUserId(), OrderStatus.Pending);
            if (optionalProduct.isPresent() && optionalUser.isPresent()) {
                product = optionalProduct.get();
                CartItems cart = new CartItems();
                cart.setProduct(product);
                cart.setPrice(product.getPrice());
                cart.setQuantity(1L);
                cart.setUser(optionalUser.get());
                cart.setOrder(runningOrder);
                cartRepo.save(cart);
                Order order = orderRepo.findByUserAndStatus(optionalUser.get(), OrderStatus.Pending);
                order.setAmount(order.getAmount() + cart.getPrice());
                order.getCartItems().add(cart);
                orderRepo.save(order);
                return ResponseEntity.status(HttpStatus.CREATED).body("Product added to the cart.");
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body("Product added to the cart.");
            }
        }
    }

    @Override
    public OrderDto getCartByUserId(Long userId) {
        Order order = orderRepo.findByUserIdAndStatus(userId, OrderStatus.Pending);
        List<CartItemsDto> cartItemsDtos = order.getCartItems().stream().map(CartItems::getCartDto).collect(Collectors.toList());
        OrderDto orderDto = new OrderDto();
        orderDto.setCartItems(cartItemsDtos);
        orderDto.setAmount(order.getAmount());
        orderDto.setId(order.getId());
        orderDto.setStatus(order.getStatus());
        return orderDto;
    }

    @Override
    public OrderDto addMinusOnProduct(QuantityChangeProductDto quantityChangeProductDto) {
        Order order = orderRepo.findByUserIdAndStatus(quantityChangeProductDto.getUserId(), OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepo.findById(quantityChangeProductDto.getProductId());
        Optional<CartItems> optionalCartItem = cartRepo.findByProductIdAndOrderIdAndUserId(quantityChangeProductDto.getProductId(), order.getId(), quantityChangeProductDto.getUserId());
        CartItems cartItem = optionalCartItem.get();
        order.setAmount(order.getAmount() - optionalProduct.get().getPrice());
        cartItem.setQuantity(optionalCartItem.get().getQuantity() - 1);
        cartRepo.save(cartItem);
        orderRepo.save(order);
        return order.getOrderDto();
    }

    @Override
    public OrderDto addPlusOnProduct(QuantityChangeProductDto quantityChangeProductDto) {
        Order order = orderRepo.findByUserIdAndStatus(quantityChangeProductDto.getUserId(), OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepo.findById(quantityChangeProductDto.getProductId());
        Optional<CartItems> optionalCartItem = cartRepo.findByProductIdAndOrderIdAndUserId(quantityChangeProductDto.getProductId(), order.getId(), quantityChangeProductDto.getUserId());
        CartItems cartItem = optionalCartItem.get();
        order.setAmount(order.getAmount() + optionalProduct.get().getPrice());
        cartItem.setQuantity(optionalCartItem.get().getQuantity() + 1);
        cartRepo.save(cartItem);
        orderRepo.save(order);
        return order.getOrderDto();
    }

    @Override
    public OrderDto PlaceOrder(PlaceOrderDto placeOrderDto) {
        Order order = orderRepo.findByUserIdAndStatus(placeOrderDto.getUserId(), OrderStatus.Pending);
        Optional<User> optionalUser = userRepo.findById(placeOrderDto.getUserId());
        order.setStatus(OrderStatus.Submitted);
        order.setDate(new Date());
        order.setAmount(order.getAmount());
        orderRepo.save(order);
        User user = optionalUser.get();
        Order newOrder = new Order();
        newOrder.setAmount(0L);
        newOrder.setUser(user);
        newOrder.setStatus(OrderStatus.Pending);
        orderRepo.save(newOrder);
        return order.getOrderDto();
    }

    @Override
    public List<OrderDto> getMyPlacedOrders(Long userId) {
        return orderRepo.findAllByUserIdAndStatus(userId, OrderStatus.Submitted).stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

}

