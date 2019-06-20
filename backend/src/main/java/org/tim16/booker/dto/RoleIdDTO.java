package org.tim16.booker.dto;

public class RoleIdDTO {

    private String role;
    private Integer userID;
    private Integer adminOf;

    public RoleIdDTO() {}

    public RoleIdDTO(String role, Integer userID) {
        this.role = role;
        this.userID = userID;
    }

    public RoleIdDTO(String role, Integer userID, Integer adminOf) {
        this.role = role;
        this.userID = userID;
        this.adminOf = adminOf;
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

    public Integer getAdminOf() {
        return adminOf;
    }

    public void setAdminOf(Integer adminOf) {
        this.adminOf = adminOf;
    }
}
