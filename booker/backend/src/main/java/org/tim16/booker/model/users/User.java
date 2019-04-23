package org.tim16.booker.model.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.userdetails.UserDetails;
import org.tim16.booker.model.utility.Rate;
import org.tim16.booker.model.utility.Authority;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String name;

    private String lastname;

    @Column(nullable = false)
    private String email;

    private String city;

    private Integer phoneNum;

    @Column(nullable = false)
    private Integer bonusPts;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType type;

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Friendship> friends = new HashSet<Friendship>();

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Reservation> reservations = new HashSet<Reservation>();

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<Rate> rates = new HashSet<Rate>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;

    @Column(name = "enabled")
    private boolean enabled;

    public User() {}

    public User(String username, String password, String name, String lastname, String email, String city,
                Integer phoneNum, Integer bonusPts, UserType type) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.city = city;
        this.phoneNum = phoneNum;
        this.bonusPts = bonusPts;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String address) {
        this.city = address;
    }

    public Integer getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(Integer phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getBonusPts() {
        return bonusPts;
    }

    public void setBonusPts(Integer bonusPts) {
        this.bonusPts = bonusPts;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Set<Friendship> getFriends() {
        return friends;
    }

    public void setFriends(Set<Friendship> friends) {
        this.friends = friends;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Set<Rate> getRates() {
        return rates;
    }

    public void setRates(Set<Rate> rates) {
        this.rates = rates;
    }

    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthority(List<Authority> authorities) {
        this.authorities = authorities;
    }


    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


}
