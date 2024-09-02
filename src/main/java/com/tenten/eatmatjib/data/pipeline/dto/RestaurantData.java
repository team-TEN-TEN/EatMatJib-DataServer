package com.tenten.eatmatjib.data.pipeline.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Setter;

@Setter
public class RestaurantData {

    private String name;

    private String zipCode;

    private String address;

    private String cuisine;

    private BigDecimal x;

    private BigDecimal y;

    private String phoneNumber;

    private String homepageUrl;

    private LocalDateTime updatedAt;
}
