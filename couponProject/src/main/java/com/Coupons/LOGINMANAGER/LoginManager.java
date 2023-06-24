package com.Coupons.LOGINMANAGER;
import com.Coupons.Exceptions.AdminException;
import com.Coupons.Services.AdminService;
import com.Coupons.Services.ClientService;
import com.Coupons.Services.CompanyService;
import com.Coupons.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class LoginManager  {
    @Autowired
    ConfigurableApplicationContext ctx;


    /**
     * this method for login with ClientType Amin,Company or Customer
     * @param email
     * @param password
     * @param clientType
     * @return the type of client
     * @throws AdminException
     */
    public ClientService login(String email, String password, ClientType clientType) throws Exception {
        switch (clientType) {
            case Admin:
                AdminService adminService = ctx.getBean(AdminService.class);
                if (adminService.login(email, password)) {
                    return adminService;
                }
                break;
            case Company:
                CompanyService companyService =ctx.getBean(CompanyService.class);
                if (companyService.login(email, password)) {
                    return companyService;
                }
                break;
            case Customer:
                CustomerService customerService = ctx.getBean(CustomerService.class);
                if (customerService.login(email, password)) {
                    return customerService;
                }
                break;
        }throw new AdminException("Wrong Email or password ");

    }
}