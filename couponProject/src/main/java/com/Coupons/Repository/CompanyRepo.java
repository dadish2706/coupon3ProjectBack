package com.Coupons.Repository;
import com.Coupons.Entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepo extends JpaRepository<Company, Integer> {

    public Company findCompanyById(int companyId);

    public Company findCompanyByEmailAndPassword(String email, String password);

    public Company getCompanyById(int companyId);
}

