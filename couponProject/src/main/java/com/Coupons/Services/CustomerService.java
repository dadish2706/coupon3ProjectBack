package com.Coupons.Services;
import com.Coupons.Entities.Category;
import com.Coupons.Entities.Coupon;
import com.Coupons.Entities.Customer;
import com.Coupons.Exceptions.CustomerException;
import org.springframework.context.annotation.Scope;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Scope("prototype")
public class CustomerService extends ClientService{

    private int customerId;


    /**
     * this method is for login of the customer
     *
     * @param email    email of the customer
     * @param password the customer password
     * @return true or false
     * @throws SQLException
     * @throws CustomerException
     */
    public boolean login(String email, String password) {
        Optional<Customer> login= Optional.ofNullable(customerRepo.findByEmailAndPassword(email, password));
        if (login.isPresent()) {
            System.out.println("successfully logged into customer with email: " + email + " and password: " + password);
            customerId=login.get().getId();
            System.out.println(customerId);
            return true;
        } else {System.out.println("Wrong email or password");

        }
        return false;
    }



    /**
     * this method is for purchase coupon by costumer by making date of today
     *   we're getting all the coupons and checking for each one of them that the date is not before from the
     * date of today and if the customer has already this coupon by the coupon id and if the
     *  amount of the coupon are more than 0.
     *  then will set amount of coupon -1
     *  and will save it to the customer
     */
    @Transactional
    public void purchaseCoupon(int couponId){
        try {
        for (Coupon c : getCustomerDetails().getCoupons()) {
            if (c.getId()== couponId) {
                throw new CustomerException("CANT PURCHASE AN EXISTING COUPON");
            }
        }

        Coupon coupon = couponRepo.getCouponById(couponId);
        LocalDate localDate = LocalDate.now();
        if (coupon.getEndDate().isBefore(localDate)) {
            throw new CustomerException("CANT PURCHASE AN OUT OF DATE COUPON ");
        }
        if (coupon.getAmount() == 0) {
            throw new CustomerException("CANT PURCHASE COUPON OUT OF STOCK");
        }
            Customer customer = customerRepo. findById(this.customerId).orElseThrow(
                    ChangeSetPersister.NotFoundException::new);
            Set<Coupon> customerCoupons = customer.getCoupons();
            customerCoupons.add(coupon);
            customer.setCoupons(customerCoupons);
            coupon.setAmount(coupon.getAmount()-1) ;
            customerRepo.save(customer);
    } catch (CustomerException | ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
        }

        /**
     * this method will delete purchase of coupon by customer by couponID
         * will set amount of coupon +1 and
         * will delete his purchase by customer
     */
    public void deleteCouponPurchaseByCustomer(int couponId) {
        Coupon coupon = couponRepo.getCouponById(couponId);
        coupon.setAmount(coupon.getAmount()+1);
        couponRepo.save(coupon);
        couponRepo.deletePurchasesByCustomer(couponId,this.customerId);

    }

    /**
     * this method getting all the customer coupons and returning a list of them.
     *
     * @return
     * @throws SQLException
     * @throws ChangeSetPersister.NotFoundException
     */
    public Set<Coupon> getCustomerCoupons() throws SQLException, ChangeSetPersister.NotFoundException {
        return getCustomerDetails().getCoupons();
    }
    /**
     * this method getting all the customer coupons  by category and
     * returning a list of them.
     *
     * @return
     * @throws SQLException
     * @throws ChangeSetPersister.NotFoundException
     */

    public List<Coupon> getCustomerCouponsByCategory(Category category) throws SQLException, ChangeSetPersister.NotFoundException {
        List<Coupon> couponList=new ArrayList<>();
        for (Coupon c:getCustomerCoupons()) {
            if (c.getCategory().equals(category)) {
                couponList.add(c);
            }
        }
            return couponList;
        }
    /**
     * this method getting all the customer coupons  by maxPrice and
     * returning a list of them.
     *
     * @return
     * @throws SQLException
     * @throws ChangeSetPersister.NotFoundException
     */

    public List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice) throws SQLException, ChangeSetPersister.NotFoundException {
        List<Coupon> couponList=new ArrayList<>();
        for (Coupon c:getCustomerCoupons()) {
            if (c.getPrice()<maxPrice) {
                couponList.add(c);
            }
        }
        return couponList;
    }

    /**
     * @return the customer details
     * @throws SQLException
     */
        public Customer getCustomerDetails(){
            return customerRepo.findCustomerById(this.customerId);
        }

}



