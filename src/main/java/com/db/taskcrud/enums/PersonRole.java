package com.db.taskcrud.enums;

public enum PersonRole {

    ADMIN("admin"),

    USER("user");

    private String role;

    PersonRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
