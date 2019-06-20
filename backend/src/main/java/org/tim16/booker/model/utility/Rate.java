package org.tim16.booker.model.utility;


import com.fasterxml.jackson.annotation.JsonBackReference;
import org.tim16.booker.model.users.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rates")
public class Rate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonBackReference("user-rates")
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer rateValue;

    public Rate() { /* empty constructor */}

    public Rate(User user, Integer rateValue) {
        this.user = user;
        this.rateValue = rateValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getRateValue() {
        return rateValue;
    }

    public void setRateValue(Integer rate) {
        this.rateValue = rate;
    }
}
