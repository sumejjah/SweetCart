package com.sweetcart.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User {



    @Id
    @GeneratedValue
    private Long id;



    @NotNull(message = "Username must be input") @Size(min = 2, max = 60, message = "size between 2 and 60")
    private String username;



    @NotNull(message = "Password must be input") @Size(min = 2, max = 60, message = "size between 2 and 60")
    private String password;




    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id")
    @NotNull
    private Role roleId;

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    public User() {
    }

    public User(@NotNull String username, @NotNull String password, @NotNull Role roleId) {
        this.username = username;
        this.password = password;
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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




    /*public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }*/
}
