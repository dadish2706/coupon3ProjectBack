package com.Coupons.Services;

import com.Coupons.Entities.Category;
import com.Coupons.Entities.Coupon;
import com.Coupons.Repository.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Scope("prototype")

public class CouponService {

    @Autowired
    protected CouponRepo couponRepo;


    public List<Coupon> getAllCoupons() throws SQLException, ChangeSetPersister.NotFoundException {
        return couponRepo.findAll();
    }
    public int getOneCoupon(int couponId) {
        couponRepo.findCouponById(couponId);
        return couponId;
    }

}
