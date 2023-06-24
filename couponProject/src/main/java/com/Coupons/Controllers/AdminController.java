package com.Coupons.Controllers;

import com.Coupons.Entities.Company;
import com.Coupons.Entities.Customer;
import com.Coupons.Entities.MySession;
import com.Coupons.Exceptions.AdminException;
import com.Coupons.Services.AdminService;
import com.Coupons.Services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;
    @Autowired
    HashMap<String, MySession> sessions;


    @PostMapping("/company/add")
    public void addCompany(@RequestBody Company company,@RequestHeader String authorization)  {
        try {
            String token =authorization.replace("Bearer ", "");
            ((AdminService)sessions.get(token).getClientService()).addCompany(company);
       } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/customer/add")
    public void addCustomer(@RequestBody Customer customer,@RequestHeader String authorization) throws Exception{
        try {
            String token =authorization.replace("Bearer ", "");
            ((AdminService)sessions.get(token).getClientService()).addCustomer(customer);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/customer/{customerId}")
    public Customer getOneCustomer(@PathVariable int customerId,@RequestHeader String authorization)
            throws ResponseStatusException {
        try {
            String token =authorization.replace("Bearer ", "");
           return  ((AdminService)sessions.get(token).getClientService()).getOneCustomer(customerId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @GetMapping("/company/{companyId}")
    public Company getOneCompany(@PathVariable int companyId,@RequestHeader String authorization)
            throws ResponseStatusException {
        try {
            String token =authorization.replace("Bearer ", "");
           return ((AdminService)sessions.get(token).getClientService()).getOneCompany(companyId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/customers/all")
    public List<Customer> getAllCustomers(@RequestHeader String authorization) {
        try {
            String token =authorization.replace("Bearer ", "");
           return  ((AdminService)sessions.get(token).getClientService()).getAllCustomers();
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/companies/all")
    public List<Company> getAllCompanies(@RequestHeader String authorization) {
        try {
               String token =authorization.replace("Bearer ", "");
            return  ((AdminService)sessions.get(token).getClientService()).getAllCompanies();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/customer/update")
    public void updateCustomer(@RequestBody Customer customer,@RequestHeader String authorization) throws ResponseStatusException {
        try {
            String token =authorization.replace("Bearer ", "");
            ((AdminService)sessions.get(token).getClientService()).updateCustomer(customer);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/company/update")
    public void updateCompany(@RequestBody Company company,@RequestHeader String authorization) throws ResponseStatusException {
        try {
            String token =authorization.replace("Bearer ", "");
            ((AdminService)sessions.get(token).getClientService()).updateCompany(company,company.getId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/company/{companyId}")
    public void deleteOneCompany(@PathVariable int companyId,@RequestHeader String authorization)
            throws ResponseStatusException {
        try {
            String token =authorization.replace("Bearer ", "");
            ((AdminService)sessions.get(token).getClientService()).deleteCompany(companyId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @DeleteMapping("/customer/{customerId}")
    public void deleteOneCustomer(@PathVariable int customerId,@RequestHeader String authorization)
            throws ResponseStatusException {
        try {
            String token =authorization.replace("Bearer ", "");
            ((AdminService)sessions.get(token).getClientService()).deleteCustomer(customerId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }



}
