package com.Coupons.Entities;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name,email,password;


    /**
     * this for one company to many coupons
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company",orphanRemoval = true)
    private List<Coupon> coupons=new ArrayList<>();

    /**
     * this constructor for making a new company or update
     *
     * @param name
     * @param email
     * @param password
     */
    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * empty constructor
     */
    public Company() {
    }

    /**
     * this constructor for getting info of the company
     *
     * @param id
     * @param name
     * @param email
     * @param password
     * @param coupons
     */
    public Company(int id, String name, String email, String password, List<Coupon> coupons) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public void add(Coupon coupon) {
        if (this.coupons == null) {
            this.coupons = new ArrayList<>();
        }
        coupon.setCompany(this);
        this.coupons.add(coupon);
    }



    @Override
    public String toString() {
            return "Company{" +
                    "companyId=" + id +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
