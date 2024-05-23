package com.demo.rrss.rrssbackend.controller;

import com.demo.rrss.rrssbackend.entity.Coupon;
import com.demo.rrss.rrssbackend.rest.request.CouponRequest;
import com.demo.rrss.rrssbackend.service.CouponService;
import com.demo.rrss.rrssbackend.service.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@RestController
public class CouponController {
    private final CouponService couponService;
    private final JwtUtil jwtUtil;

    public CouponController(CouponService couponService, JwtUtil jwtUtil) {
        this.couponService = couponService;
        this.jwtUtil = jwtUtil;
    }

    @ModelAttribute
	public void addUserIdToModel(@RequestHeader(value="Authorization", required=false) String bearerToken, Model model) {
		if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Authorization header is missing or invalid");
		}
		String token = bearerToken.substring(7);
		Long userId;
		try {
			userId = jwtUtil.extractUserId(token);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token");
		}
		if (userId == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User ID not found in token");
		}
		model.addAttribute("userId", userId);
	}

    @GetMapping("/get-coupon")
    @ResponseStatus(HttpStatus.OK)
    public Coupon getCoupon(@RequestParam Long userId) {
        return couponService.getCoupon(userId);
    }

    @GetMapping("/user-get-coupon")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Coupon>> getUserCoupon(Model model) {
        Long userId = (Long) model.getAttribute("userId");
        return couponService.getCouponByUserId(userId);
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
