package com.solo.api.models;

import jakarta.persistence.*;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(length = 15, nullable = false)
    private String nickname;

    @Column(length = 20, nullable = false)
    private String password;

    @Column (length = 10, nullable = false)
    private String birthday;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 11, nullable = false)
    private String phone;

    @Column
    private double weight;

    @Column
    private double height;

    @Lob
    @Column
    private byte[] profile_pic;

    public User(Integer userId, String nickname, String password, String birthday, String email, String phone, double weight, double height, byte[] profile_pic){
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        this.birthday = birthday;
        this.email = email;
        this.phone = phone;
        this.weight = weight;
        this.height = height;
        this.profile_pic = profile_pic;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public byte[] getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(byte[] profile_pic) {
        this.profile_pic = profile_pic;
    }


}
