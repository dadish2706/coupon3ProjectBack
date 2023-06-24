package com.Coupons.Controllers;

import com.Coupons.Entities.Category;
import com.Coupons.Entities.Company;
import com.Coupons.Entities.Coupon;
import com.Coupons.Entities.MySession;
import com.Coupons.Exceptions.AdminException;
import com.Coupons.Services.ClientService;
import com.Coupons.Services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    HashMap<String, MySession> sessions;




    @PostMapping("/addCoupon")
    public void addCoupon(@RequestBody Coupon coupon,@RequestHeader String authorization)  {
        try {
            String token =authorization.replace("Bearer ", "");
            ((CompanyService)sessions.get(token).getClientService()).addCoupon(coupon);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{couponId}")
    public void deleteCoupon(@PathVariable int couponId,@RequestHeader String authorization)
            throws ResponseStatusException {
        try {
            String token =authorization.replace("Bearer ", "");
            ((CompanyService)sessions.get(token).getClientService()).deleteCoupon(couponId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //get all coupons by max price:
    @GetMapping("/allCouponsByPrice/{maxPrice}")
    public List<Coupon> getCouponByMaxPrice(@PathVariable double maxPrice,@RequestHeader String authorization )
            throws ResponseStatusException {
        try {
            String token =authorization.replace("Bearer ", "");
           return ((CompanyService)sessions.get(token).getClientService()).getCouponsByMaxPrice(maxPrice);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //get all coupons:

    @GetMapping("/allCoupons")
    public List<Coupon> getAllCoupons(@RequestHeader String authorization) {
        try {
            String token =authorization.replace("Bearer ", "");
            return ((CompanyService)sessions.get(token).getClientService()).getCompanyCoupons();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //get company info:

    @GetMapping("/companyInfo")
    public Company getCompanyInfo(@RequestHeader String authorization)
            throws ResponseStatusException {
        try {
            String token =authorization.replace("Bearer ", "");
            return ((CompanyService)sessions.get(token).getClientService()).getCompanyDetails();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //get coupon:
    @GetMapping("/{couponId}")
    public Coupon getOneCoupon(@PathVariable int couponId,@RequestHeader String authorization)
            throws ResponseStatusException {
        try {
            String token =authorization.replace("Bearer ", "");
            return ((CompanyService)sessions.get(token).getClientService()).getCompanyCoupons().get(couponId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    //get coupon by Category
    @GetMapping("/allCouponsByCategory/{category}")
    public List<Coupon> getCouponByCategory(@PathVariable Category category,@RequestHeader String authorization)
            throws ResponseStatusException {
        try {
            String token =authorization.replace("Bearer ", "");
            return ((CompanyService)sessions.get(token).getClientService()).getCompanyCouponsByCategory(category);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //update coupon:
    @PutMapping("/updateCoupon")
    public void updateCoupon(@RequestBody Coupon coupon,@RequestHeader String authorization) throws ResponseStatusException {
        try {
            String token =authorization.replace("Bearer ", "");
             ((CompanyService)sessions.get(token).getClientService()).updateCoupon(coupon);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


}


