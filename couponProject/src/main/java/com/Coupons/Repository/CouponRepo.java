package com.Coupons.Repository;

import com.Coupons.Entities.Category;
import com.Coupons.Entities.Company;
import com.Coupons.Entities.Coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface CouponRepo extends JpaRepository<Coupon,Integer> {

    public int findCouponById(int couponId);

    public List<Coupon> findCouponByCompany(Company company);

//    @Modifying
//    @Transactional
//    @Query(value = "UpDate Coupons.coupons SET amount=amount+1 where id=:id", nativeQuery = true)
//    Coupon deletePorchesCoupon(@Param(value = "id") int couponId);



    boolean existsByTitleAndCompanyId(String title, int companyId);

    List<Coupon> findCouponsByCompanyId(int companyId);

    List<Coupon> findAllCouponsByCategory(Category category);

    List<Coupon> getAllCouponsByCategory(Category category);

    List<Coupon> findByPriceLessThan(Double maxPrice);

    Coupon getCouponById(int couponId);

    List<Coupon> findAll();
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Coupons.customer_coupon WHERE coupon_id=:coupon_id and customer_id=:customer_id",nativeQuery = true)
    void deletePurchasesByCustomer(@Param("coupon_id") int couponId, @Param("customer_id") int customerId);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Coupons.customer_coupon WHERE coupon_id=:coupon_id",nativeQuery = true)
    void deletePurchasesByCompany(@Param(value = "coupon_id") int couponId);

    List<Coupon> findByEndDateLessThanEqual(LocalDate now);
    @Procedure(value ="deleteCouponPurchaseHistory")
    public void deleteCouponPurchaseHistory(int couponId);


}
