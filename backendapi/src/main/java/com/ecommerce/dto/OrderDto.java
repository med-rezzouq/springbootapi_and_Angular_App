package com.ecommerce.dto;

import com.ecommerce.entity.CartItems;
import com.ecommerce.enums.OrderStatus;
import lombok.*;

import java.util.Date;
import java.util.List;


@Data
public class OrderDto {

    private List<CartItemsDto> cartItems;

    private Long id;

    private Date date;

    private Long amount;

    private OrderStatus status;

    private String userName;
}
