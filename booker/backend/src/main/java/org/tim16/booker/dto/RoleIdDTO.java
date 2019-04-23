package org.tim16.booker.dto;

public class RoleIdDTO {

    private String role;
    private Integer userID;

    public RoleIdDTO() {}

    public RoleIdDTO(String role, Integer userID) {
        this.role = role;
        this.userID = userID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }
}
