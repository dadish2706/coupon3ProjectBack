package com.Coupons.Controllers;

import com.Coupons.Entities.Category;
import com.Coupons.Entities.Coupon;
import com.Coupons.Entities.Customer;
import com.Coupons.Entities.MySession;
import com.Coupons.Services.ClientService;
import com.Coupons.Services.CompanyService;
import com.Coupons.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    HashMap<String, MySession> sessions;


// add coupon purchase
    @PostMapping("/addCouponPurchase")
    public void addCouponPurchase(@RequestParam int couponId,@RequestHeader String authorization)  {
        try {
            String token =authorization.replace("Bearer ", "");
            ((CustomerService)sessions.get(token).getClientService()).purchaseCoupon(couponId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    //get all coupons by max price:
    @GetMapping("/allCouponsByPrice/{maxPrice}")
    public List<Coupon> getCouponByMaxPrice(@PathVariable double maxPrice,@RequestHeader String authorization )
            throws ResponseStatusException {
        try {
            String token =authorization.replace("Bearer ", "");
            return  ((CustomerService)sessions.get(token).getClientService()).getCustomerCouponsByMaxPrice(maxPrice);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //get all coupons:

    @GetMapping("/allCoupons")
    public Set<Coupon> getAllCoupons(@RequestHeader String authorization) {
        try {
            String token =authorization.replace("Bearer ", "");
            return  ((CustomerService)sessions.get(token).getClientService()).getCustomerCoupons();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //get customer info:

    @GetMapping("/customerInfo")
    public Customer getCustomrInfo(@RequestHeader String authorization)
            throws ResponseStatusException {
        try {
            String token =authorization.replace("Bearer ", "");
           return  ((CustomerService)sessions.get(token).getClientService()).getCustomerDetails();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //get coupon by Category
    @GetMapping("/allCouponsByCategory/{categoryName}")
    public List<Coupon> getCouponByCategory(@PathVariable Category category,@RequestHeader String authorization)
            throws ResponseStatusException {
        try {
            System.out.println("categoryName " + category);
            String token =authorization.replace("Bearer ", "");
           return ((CustomerService)sessions.get(token).getClientService()).getCustomerCouponsByCategory(category);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


}
