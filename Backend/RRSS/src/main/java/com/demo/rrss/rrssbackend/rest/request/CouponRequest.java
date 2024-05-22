package com.demo.rrss.rrssbackend.rest.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data

public class CouponRequest {
    private Long userId;
    private String couponText;
    private String couponData;
}
