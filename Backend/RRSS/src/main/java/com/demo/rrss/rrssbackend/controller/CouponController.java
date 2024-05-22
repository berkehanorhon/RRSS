package com.demo.rrss.rrssbackend.controller;

import com.demo.rrss.rrssbackend.entity.Coupon;
import com.demo.rrss.rrssbackend.rest.request.CouponRequest;
import com.demo.rrss.rrssbackend.service.CouponService;
import com.demo.rrss.rrssbackend.service.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class CouponController {
    private final CouponService couponService;
    private final JwtUtil jwtUtil;

    public CouponController(CouponService couponService, JwtUtil jwtUtil) {
        this.couponService = couponService;
        this.jwtUtil = jwtUtil;
    }

    @ModelAttribute
    public void addUserIdToModel(@RequestHeader(value="Authorization") String bearerToken, Model model) {
        String token = bearerToken.substring(7);
        Long userId = jwtUtil.extractUserId(token);
        model.addAttribute("userId", userId);
    }

    @GetMapping("/get-coupon")
    @ResponseStatus(HttpStatus.OK)
    public Coupon getCoupon(@RequestParam Long userId) {
        return couponService.getCoupon(userId);
    }

    @PostMapping("/add-coupon")
    @ResponseStatus(HttpStatus.CREATED)
    public Coupon addCoupon(@RequestBody CouponRequest request, Model model) {
        return couponService.addCoupon(request, model);
    }

    @PatchMapping("/update-coupon")
    @ResponseStatus(HttpStatus.OK)
    public Coupon updateCoupon(@RequestParam Long couponId, @RequestBody CouponRequest request) {
        return couponService.updateCoupon(couponId, request);
    }

    @DeleteMapping("/delete-coupon")
    public void deleteCoupon(@RequestParam Long couponId) {
        couponService.deleteCoupon(couponId);
    }
}
