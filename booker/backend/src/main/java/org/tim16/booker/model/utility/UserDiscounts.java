package org.tim16.booker.model.utility;

import org.tim16.booker.model.users.UserType;

import javax.persistence.*;

@Entity
@Table(name = "discounts")
public class UserDiscounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Enumerated
    @Column(nullable = false, unique = true)
    private UserType userType;

    private Integer minPts;

    private Integer discount;

    public UserDiscounts() { /* empty constructor */}

    public UserDiscounts(UserType userType, Integer minPts, Integer discount) {
        this.userType = userType;
        this.minPts = minPts;
        this.discount = discount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Integer getMinPts() {
        return minPts;
    }

    public void setMinPts(Integer minPts) {
        this.minPts = minPts;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
