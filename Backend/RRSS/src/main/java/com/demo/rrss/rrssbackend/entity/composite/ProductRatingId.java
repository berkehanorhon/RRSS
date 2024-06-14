package com.demo.rrss.rrssbackend.entity.composite;

import java.io.Serializable;
import java.util.Objects;

public class ProductRatingId implements Serializable {
    private Long userId;
    private Long productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRatingId that = (ProductRatingId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, productId);
    }
}
