package com.demo.rrss.rrssbackend.service;

import com.demo.rrss.rrssbackend.entity.Coupon;
import com.demo.rrss.rrssbackend.repository.CouponRepository;
import com.demo.rrss.rrssbackend.repository.UsersRepository;
import com.demo.rrss.rrssbackend.rest.request.CouponRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final UsersRepository userRepository;

    private static final List<String> COUPON_DESCRIPTIONS =
            Arrays.asList("10% discount on all products", "20% discount on all products",
                    "30% discount on all products", "40% discount on all products",
                    "50% discount on all products", "60% discount on all products",
                    "70% discount on all products", "80% discount on all products",
                    "Free shipping on all products", "Free shipping on orders over 10$",
                    "Free shipping on orders over 20$", "Free shipping on orders over 30$",
                    "Free shipping on orders over 40$", "Free shipping on orders over 50$");

    public CouponService(CouponRepository couponRepository, UsersRepository userRepository) {
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
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

        Long userId = request.getUserId();
        if (userId != null) {
            if (userRepository.findById(userId).isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        } else {
            userId = (Long) model.getAttribute("userId");
        }

        Coupon coupon = new Coupon();
        coupon.setUserId(userId);
        Random rand = new Random();
        int randomIndex = rand.nextInt(COUPON_DESCRIPTIONS.size());
        coupon.setCouponText(request.getCouponText() == null ? COUPON_DESCRIPTIONS.get(randomIndex) : request.getCouponText());
        coupon.setCouponData(UUID.randomUUID().toString().replace("-", "").substring(0, 12));
        couponRepository.save(coupon);
        return coupon;
    }

    public Coupon updateCoupon(Long couponId, CouponRequest request) {
        Optional<Coupon> existingCoupon = couponRepository.findById(couponId);
        if (existingCoupon.isPresent()) {
            Coupon coupon = existingCoupon.get();
            coupon.setCouponText(request.getCouponText());
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
