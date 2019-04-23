package org.tim16.booker.dto;

public class ProfileDTO {
    private Integer id;
    private String username;
    private String name;
    private String lastname;
    private String email;
    private String address;
    private Integer phoneNum;

    public ProfileDTO() {}

    public ProfileDTO(Integer id, String username, String name, String lastname, String email, String address, Integer phoneNum) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Integer getPhoneNum() {
        return phoneNum;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNum(Integer phoneNum) {
        this.phoneNum = phoneNum;
    }
}
