package com.Coupons.Services;
import com.Coupons.Entities.Company;
import com.Coupons.Entities.Coupon;
import com.Coupons.Entities.Customer;
import com.Coupons.Exceptions.AdminException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@Scope("prototype")
public class AdminService extends ClientService{



    public boolean login(String email,String password){
        if (email.equals("admin@admin.com") && password.equals("admin")){
            return true;
        }
        return false;

    }

    /**
     * this method is for adding new company
     * first checking if the company already exist by getting a list of all the companies and for each company checking
     * the name and email if one of them is equal so the company exist, and it will throw sql exception if not
     * it will be continued to adding a new company
     *
     * @param company a new company that will be added by the admin
     * @throws SQLException
     * @throws AdminException if company already exist will throw sql message.
     */
    public void addCompany(Company company) throws AdminException {
        List<Company> companyList = companyRepo.findAll();
        for (Company comp : companyList) {
            if (comp.getName().equals(company.getName()) || comp.getEmail().equals(company.getEmail()))
                throw new AdminException("Company and name are already exist");
        }
        companyRepo.save(company);

    }

    /**
     * this method for updating company by checking if company id is present so it ok
     * and update the company else throw exception
     *
     * @param company
     * @param companyId
     * @return
     * @throws AdminException
     */
    public Company updateCompany(Company company, int companyId) throws AdminException {
        if (companyRepo.findById( companyId).isPresent()) {
            return companyRepo.save(company);
        } else throw new AdminException("COULD NOT UPDATE COMPANY :: COMPANY NOT FOUND EXCEPTION");
    }

    /**
     * this is a @Transactional method that will start process if while the process
     * something is not working the process will roll back
     * deleting the company by id that the admin want to delete
     * first checking if there is company by her id
     * then will delete her with her coupons
     * if not throw an exception.
     *
     * @param companyID
     * @throws AdminException
     */
    @Transactional
    public void deleteCompany(int companyID) throws AdminException {
        for (Coupon coupon:companyRepo.getCompanyById(companyID).getCoupons()){
            couponRepo.deleteCouponPurchaseHistory(coupon.getId());
            couponRepo.delete(coupon);
        }
        if (companyRepo.findById(companyID).isPresent()) {
            companyRepo.deleteById(companyID);
        } else throw new AdminException("Company not exist");
    }

    /**
     * getting a list of all companies
     *
     * @return list of all companies
     */

    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    /**
     * getting one company with her coupons
     *
     * @param companyID
     * @return one company info
     * @throws AdminException
     */
    public Company getOneCompany(int companyID) throws AdminException {
        return companyRepo.findById(companyID).orElseThrow(() ->
                new AdminException("There is no company with id :" + companyID + " like this"));
    }

    /**
     * adding a new customer by checking if there is customer by his email if there is will throw exception
     * if not add new customer
     *
     * @param customer
     * @throws SQLException
     * @throws AdminException
     */
    public void addCustomer(Customer customer) throws AdminException {
        if (customerRepo.existsByEmail(customer.getEmail())) {
            throw new AdminException("NEW CUSTOMER CANT HAVE AN EXISTING EMAIL");
        } else customerRepo.save(customer);
    }


    /**
     * updating the customer details
     *
     * @param customer
     * @throws AdminException
     */
    public void updateCustomer(Customer customer) throws AdminException {
        if (customerRepo.existsById(customer.getId())) {
            customerRepo.save(customer);
        }else throw new AdminException("not found");

    }

    /**
     * deleting a customer by his id .
     * first checking if there is customer then deleting his purchase coupons then deleting the customer.
     *
     * @param customerID
     * @throws SQLException
     * @throws
     */
    public void deleteCustomer(int customerID) throws AdminException {
        try {
            // Retrieve the customer by ID
            Optional<Customer> optionalCustomer = customerRepo.findById(customerID);
            if (optionalCustomer.isPresent()) {
                Customer customer = optionalCustomer.get();

                // Delete the customer's purchased coupons
                for (Coupon coupon : customer.getCoupons()) {
                    couponRepo.deletePurchasesByCustomer(coupon.getId(), customerID);
                }

                // Delete the customer
                customerRepo.deleteById(customerID);
            } else {
                throw new AdminException("Customer not found");
            }
        } catch (Exception e) {
            throw new AdminException("Failed to delete customer");
        }
    }



    /**
     * getting a list of all customers
     *
     * @return list of all customers
     * @throws SQLException
     */
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    /**
     * getting one customer with his coupons
     *
     * @param customerID
     * @return customer with coupons
     * @throws AdminException
     */
    public Customer getOneCustomer(int customerID) throws SQLException, AdminException {
        if (customerRepo.findById(customerID).isPresent()) {
            Customer customer = customerRepo.findById(customerID).get();
            return customer;
        } else throw new AdminException("CUSTOMER NOT FOUND BY ID");
    }

}







