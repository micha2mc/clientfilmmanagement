package com.zakado.zkd.clientfilmmanagement.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private Boolean enable;

    private List<Rol> roles;

    private List<Reviews> reviews;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", enable=" + enable +
                ", roles=" + roles +
                ", reviews=" + reviews +
                '}';
    }
}
