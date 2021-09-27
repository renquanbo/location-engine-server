package com.breadcrumbdata.locationengineserver.model.dto;


import com.breadcrumbdata.locationengineserver.model.enums.Role;
import com.breadcrumbdata.locationengineserver.util.validator.RolePattern;

import javax.validation.constraints.NotBlank;

public class UserDTO {

    private Long id;

    @NotBlank
    private String email;

    @NotBlank(message = "Password must not be empty")
    private String password;

    @RolePattern(regexp = "ROLE_USER|ROLE_MANAGER", message = "Role must not be empty")
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
