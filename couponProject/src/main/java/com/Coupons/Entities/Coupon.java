package com.Coupons.Entities;
import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;


@Entity
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * many coupons to one company
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
//

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Category category;
    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private LocalDate startDate, endDate;
    @Column(nullable = false)
    private int amount;
    @Column(nullable = false)
    private double price;

    private String image;



    /**
     * this constructor for making a new coupon or update
     *
     * @param
     * @param category
     * @param title
     * @param description
     * @param startDate
     * @param endDate
     * @param amount
     * @param price
     * @param image
     */
    public Coupon( Category category, String title, String description,
                  Date startDate, Date endDate, int amount, double price, String image) {

        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate.toLocalDate();
        this.endDate = endDate.toLocalDate();
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    /**
     * this constructor for getting info of the coupon
     *
     * @param id
     * @param
     * @param category
     * @param title
     * @param description
     * @param startDate
     * @param endDate
     * @param amount
     * @param price
     * @param image
     */
    public Coupon(int id,Category category, String title,
                  String description, Date startDate, Date endDate, int amount, double price, String image,Company company) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate.toLocalDate();
        this.endDate = endDate.toLocalDate();
        this.amount = amount;
        this.price = price;
        this.image = image;
        this.company=company;
    }

    /**
     * empty contactor
     */
    public Coupon() {
    }

    public int getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate.toLocalDate();
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate.toLocalDate();
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return '\n'+"Coupon{" +
                "id=" + id +
                ", company=" + company +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +'\n'+
                '}';
    }
}
