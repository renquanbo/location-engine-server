package com.breadcrumbdata.locationengineserver.model.vo;

import com.breadcrumbdata.locationengineserver.model.enums.Role;

import javax.validation.constraints.NotBlank;

public class UserVO {
    private Long id;

    private String email;

    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
