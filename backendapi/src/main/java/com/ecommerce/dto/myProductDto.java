package com.ecommerce.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class myProductDto {

    private Long id;

    private String name;

    private Long availableQuantity;

    private Long price;

    private String description;

    private MultipartFile img;

    private byte[] returnedImg;

}
