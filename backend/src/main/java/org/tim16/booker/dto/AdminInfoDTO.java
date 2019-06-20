package org.tim16.booker.dto;

public class AdminInfoDTO {
    private String username;
    private String password;
    private String name;
    private String lastName;
    private String email;
    private String city;
    private Integer phoneNum;
    private String adminOf;
    private Integer itemID;

    public AdminInfoDTO() {}

    public AdminInfoDTO(String username, String password, String name, String lastName, String email, String city,
                        Integer phoneNum, String adminOf, Integer itemID) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.phoneNum = phoneNum;
        this.adminOf = adminOf;
        this.itemID = itemID;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(Integer phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAdminOf() {
        return adminOf;
    }

    public void setAdminOf(String adminOf) {
        this.adminOf = adminOf;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }
}
