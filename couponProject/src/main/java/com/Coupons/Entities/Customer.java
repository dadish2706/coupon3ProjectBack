package com.Coupons.Entities;
import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String firstName, email, password, lastName;
    /**
     * many customers to many coupons
     */
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH,CascadeType.MERGE,
                    CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinTable(
            name="customer_coupon",
            joinColumns = @JoinColumn(name="customer_id"),
            inverseJoinColumns = @JoinColumn(name="coupon_id")
    )
    private Set<Coupon> coupons;




    /**
     * this constructor for making a new customer or update.
     *
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     */
    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.lastName = lastName;
    }

    /**
     * this constructor for getting info of the customer
     *
     * @param id
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @param coupons
     */
    public Customer(int id, String firstName, String lastName,
                    String email, String password, Set<Coupon> coupons) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.coupons = coupons;

    }

    /**
     * empty constructor
     */
    public Customer() {
    }

    public int getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(Set<Coupon> coupons) {
        this.coupons = coupons;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", lastName='" + lastName + '\'' +
                ", coupons=" + coupons+   "\n" +
                "}";


    }
}
