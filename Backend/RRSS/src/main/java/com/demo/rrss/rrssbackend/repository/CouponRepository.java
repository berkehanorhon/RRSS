package com.demo.rrss.rrssbackend.repository;

import com.demo.rrss.rrssbackend.entity.Coupon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends CrudRepository<Coupon, Long> {

}
