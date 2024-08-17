package com.solo.api.models;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "SoloUser", schema = "appSolo")
public class SoloUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 15, nullable = false)
    private String nickname;

    @Column(length = 20, nullable = false)
    private String pwd;

    @Column (length = 10, nullable = false)
    private Date birthday;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 11, nullable = false)
    private String phone;

    @Column (nullable = false)
    private double weight;

    @Column (nullable = false)
    private double height;

    @Lob
    @Column (nullable = false)
    private byte[] profile_pic;

    public SoloUser(){

    }

    public SoloUser(int id, String nickname, String pwd, Date birthday, String email, String phone, double weight, double height, byte[] profile_pic){
        this.id = id;
        this.nickname = nickname;
        this.pwd = pwd;
        this.birthday = birthday;
        this.email = email;
        this.phone = phone;
        this.weight = weight;
        this.height = height;
        this.profile_pic = profile_pic;
    }

    public int getid() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SoloUser user = (SoloUser) o;
        return Double.compare(weight, user.weight) == 0 && Double.compare(height, user.height) == 0 && Objects.equals(id, user.id) && Objects.equals(nickname, user.nickname) && Objects.equals(pwd, user.pwd) && Objects.equals(birthday, user.birthday) && Objects.equals(email, user.email) && Objects.equals(phone, user.phone) && Arrays.equals(profile_pic, user.profile_pic);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, nickname, pwd, birthday, email, phone, weight, height);
        result = 31 * result + Arrays.hashCode(profile_pic);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", pwd='" + pwd + '\'' +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                "}";
    }
}
