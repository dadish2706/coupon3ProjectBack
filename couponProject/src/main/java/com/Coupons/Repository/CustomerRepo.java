package com.Coupons.Repository;

import com.Coupons.Entities.Coupon;
import com.Coupons.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndPassword(String email,String password);

    public Customer findByEmailAndPassword(String email, String password);


    Customer findCustomerById(int customerId);

    @Procedure(value ="getCustomerCoupons")
    List<Coupon> getCustomerCoupons(int customerId);
}
