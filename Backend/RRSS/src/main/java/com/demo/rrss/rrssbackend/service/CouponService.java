package com.demo.rrss.rrssbackend.service;

import com.demo.rrss.rrssbackend.entity.Coupon;
import com.demo.rrss.rrssbackend.repository.CouponRepository;
import com.demo.rrss.rrssbackend.rest.request.CouponRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CouponService {
    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Coupon getCoupon(Long couponId) {
        Optional<Coupon> coupon =  couponRepository.findById(couponId);
        return coupon.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coupon not found"));
    }

    public ResponseEntity<List<Coupon>> getCouponByUserId(Long userId) {
        List<Coupon> coupon =  couponRepository.findCouponByUserId(userId);
        return ResponseEntity.ok(coupon);
    }

    public Coupon addCoupon(CouponRequest request, Model model) {
        // Bu kısımda merchant mantığını kurgulamaya çalıştım, eğer requestten userId geliyorsa merchant vermiş demektir.
        // Eğer gelmiyorsa kuponu sistem vermiş demektir. (Model)
        Long userId = request.getUserId() == null ? (Long) model.getAttribute("userId") : request.getUserId();
        Coupon coupon = new Coupon();
        coupon.setUserId(userId);
        coupon.setCouponData(request.getCouponData());
        couponRepository.save(coupon);
        return coupon;
    }

    public Coupon updateCoupon(Long couponId, CouponRequest request) {
        Optional<Coupon> existingCoupon = couponRepository.findById(couponId);
        if (existingCoupon.isPresent()) {
            Coupon coupon = existingCoupon.get();
            coupon.setCouponData(request.getCouponData());
            couponRepository.save(coupon);
            return coupon;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coupon not found.");
        }
    }

    public void deleteCoupon(Long couponId) {
        Optional<Coupon> coupon = couponRepository.findById(couponId);
        if (coupon.isPresent()) {
            couponRepository.deleteById(couponId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coupon not found.");
        }
    }
}
