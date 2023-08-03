package com.ecommerce.entity;

import com.ecommerce.dto.myProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long availableQuantity;

    private Long price;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    private byte[] img;

    public void getProductEntity(myProductDto myProductDto) {
        this.name = myProductDto.getName();
        this.price = myProductDto.getPrice();
        this.availableQuantity = myProductDto.getAvailableQuantity();
        this.description = myProductDto.getDescription();
    }

    public myProductDto getProductDto() {
        myProductDto myProductDto = new myProductDto();
        myProductDto.setId(id);
        myProductDto.setName(name);
        myProductDto.setAvailableQuantity(availableQuantity);
        myProductDto.setPrice(price);
        myProductDto.setDescription(description);
        myProductDto.setReturnedImg(img);
        return myProductDto;
    }
}
