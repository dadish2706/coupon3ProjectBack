package com.Coupons.Services;
import com.Coupons.Exceptions.AdminException;
import com.Coupons.Repository.CompanyRepo;
import com.Coupons.Repository.CouponRepo;
import com.Coupons.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class ClientService {
	/**
	 * this class is for connecting between the services to the repository
	 */
	@Autowired
	protected CompanyRepo companyRepo;
	@Autowired
	protected CouponRepo couponRepo;
	@Autowired
	protected CustomerRepo customerRepo;
	
/**
 * system login 
 * @param email
 * @param password
 * @return
 * @throws AdminException
 */
	public abstract boolean login(String email, String password) throws AdminException;
	

}
