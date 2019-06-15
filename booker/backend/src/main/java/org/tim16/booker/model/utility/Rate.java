package org.tim16.booker.model.utility;


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

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    private User user;

    @Enumerated
    @Column(nullable = false)
    private RateType type;

    @Column(nullable = false)
    private Integer rateValue;

    public Rate() { /* empty constructor */}

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

    public RateType getType() {
        return type;
    }

    public void setType(RateType type) {
        this.type = type;
    }

    public Integer getRateValue() {
        return rateValue;
    }

    public void setRateValue(Integer rate) {
        this.rateValue = rate;
    }
}
