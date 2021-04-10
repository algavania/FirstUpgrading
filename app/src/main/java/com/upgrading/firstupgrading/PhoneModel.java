package com.upgrading.firstupgrading;

import java.io.Serializable;

public class PhoneModel implements Serializable {
    private String name, email, phone, gender, about;
    private int age;

    public PhoneModel(String name, String email, String phone, String gender, String about, int age) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.about = about;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
