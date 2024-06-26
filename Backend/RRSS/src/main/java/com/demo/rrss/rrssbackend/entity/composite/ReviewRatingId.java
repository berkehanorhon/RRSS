package com.demo.rrss.rrssbackend.entity.composite;

import java.io.Serializable;
import java.util.Objects;

public class ReviewRatingId implements Serializable {
    private Long userId;
    private Long reviewId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewRatingId that = (ReviewRatingId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(reviewId, that.reviewId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, reviewId);
    }
}
