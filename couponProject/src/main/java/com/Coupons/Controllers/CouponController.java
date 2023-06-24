package com.Coupons.Controllers;
import com.Coupons.Entities.Coupon;
import com.Coupons.Services.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/allCoupons")
public class CouponController {
@Autowired
    private CouponService couponService;


    @GetMapping("/getAll")
    public List<Coupon> getAllCouponsAll() {
        try {
            return  couponService.getAllCoupons();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @GetMapping("/getOneCoupon")
    public int getOneCoupon(int couponId) {
        try {
            return couponService.getOneCoupon(couponId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
