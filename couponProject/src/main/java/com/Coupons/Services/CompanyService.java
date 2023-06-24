package com.Coupons.Services;

import com.Coupons.Entities.Category;
import com.Coupons.Entities.Company;
import com.Coupons.Entities.Coupon;
import com.Coupons.Exceptions.CompanyException;
import org.springframework.context.annotation.Scope;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Scope("prototype")
public class CompanyService extends ClientService {

    private int companyId;

    /**
     * this method for login of company getting from repo the company if exist
     * then taking the companyId and use it for next uses and bring back true or false
     * for the login
     *
     * @param email
     * @param password
     * @return true or false for login
     */


    public boolean login(String email, String password) {
        Optional<Company> login = Optional.ofNullable(companyRepo.findCompanyByEmailAndPassword(email, password));
        if (login.isPresent()) {
            System.out.println("successfully logged into company with email: " + email + " and password: " + password);
            companyId = login.get().getId();
            System.out.println(companyId);
            return true;
        } else {
            System.out.println("Wrong email or password");

        }
        return false;
    }


    /**
     * chacking if coupon exist by title and company id
     *
     * @param title
     * @param companyId
     * @return
     */
    public boolean isCouponExistByTitleAndCompanyId(String title, int companyId) {
        return couponRepo.existsByTitleAndCompanyId(title, companyId);

    }

    /**
     * this method for adding new coupon will check if the company logged in has already
     * coupon by same title if true will throw exception
     * if false will add new coupon to the company and then will save the company with the add new coupon
     *
     * @param
     * @return
     * @throws Exception
     */


    @Transactional
    public void addCoupon(Coupon coupon) throws CompanyException {
        Company company = getCompanyDetails();
        List<Coupon> couponList = couponRepo.findCouponsByCompanyId(this.companyId);
        for (Coupon coupon1 : couponList) {
            if (coupon1.getTitle().equals(coupon.getTitle())) {
                throw new CompanyException("Title already exist");
            }
        }
        company.add(coupon);
        companyRepo.save(company);
    }

    /**
     * this method for update coupon of the company
     *
     * @param coupon
     */
    public void updateCoupon(Coupon coupon) {
        coupon.setCompany(getCompanyDetails());
        couponRepo.save(coupon);

    }

    /**
     * this method for deleting coupon first check if coupon exist by id
     * then delete purchase of the coupon then delete it by id
     */
    public void deleteCoupon(int couponId) throws CompanyException {
        if (!couponRepo.existsById(couponId))
            throw new CompanyException("Coupon not found");
        couponRepo.deletePurchasesByCompany(couponId);
        couponRepo.deleteById(couponId);
        System.out.println("Coupon no' " + couponId + " was deleted");
    }

    /**
     * this method for getting all company coupons from
     * getCompanyDetail method
     *
     * @return
     * @throws ChangeSetPersister.NotFoundException
     */
    public List<Coupon> getCompanyCoupons() throws ChangeSetPersister.NotFoundException {
        return getCompanyDetails().getCoupons();
    }

    /**
     * this method will get company coupons by category
     *
     * @param category
     * @return list of company coupons by category
     */
    public List<Coupon> getCompanyCouponsByCategory(Category category) throws ChangeSetPersister.NotFoundException {
        List<Coupon> couponList = new ArrayList<>();
        for (Coupon c : getCompanyCoupons()) {
            if (c.getCategory().equals(category)) {
                couponList.add(c);
            }
        }
        return couponList;
    }

    /**
     * this method will get company coupons by maxPrice
     *
     * @param maxPrice
     * @return list of company coupons by maxPrice
     */
    public List<Coupon> getCouponsByMaxPrice(Double maxPrice) throws ChangeSetPersister.NotFoundException {
        List<Coupon> couponList = new ArrayList<>();
        for (Coupon c : getCompanyCoupons()) {
            if (c.getPrice() <= (maxPrice)) {
                couponList.add(c);
            }
        }
        return couponList;
    }

    /**
     * this method will get all information of comapny
     *
     * @param
     * @return
     */
    public Company getCompanyDetails() {
        return companyRepo.findCompanyById(companyId);
    }


}











