package com.solo.api.models;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserMood", schema = "appSolo")
public class UserMood {
    
    @Id
    private Integer idUser;

    @OneToOne
    @MapsId
    @JoinColumn(name = "idUser", referencedColumnName = "id")
    @JsonIgnore
    private SoloUser user;

    @Column(nullable = false)
    private Date moodDate;

    @Column(nullable = false, length = 15)
    private String mood;

    public Integer getIdUser() {
        return idUser;
    }
    
    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public SoloUser getUser() {
        return user;
    }

    public void setUser(SoloUser user) {
        this.user = user;
    }

    public Date getMoodDate() {
        return moodDate;
    }
    
    public void setMoodDate(Date moodDate) {
        this.moodDate = moodDate;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
