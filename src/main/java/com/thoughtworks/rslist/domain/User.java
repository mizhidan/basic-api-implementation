package com.thoughtworks.rslist.domain;

import com.sun.istack.NotNull;

import javax.validation.constraints.*;

public class User {
    @NotNull
    @Size(max = 8)
    private String username;

    @NotNull
    private String gender;

    @NotNull
    @Min(18)
    @Max(100)
    private int age;

    @Email
    private String email;

    @Pattern(regexp = "1\\d{10}")
    private String phone;
    private int vateNum = 10;

    public User(String username, String gender, int age, String email, String phone) {
        this.username = username;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
