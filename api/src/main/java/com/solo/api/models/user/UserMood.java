package com.solo.api.models.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "UserMood", schema = "appSolo")
public class UserMood {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMood;

    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "id")
    private SoloUser user;

    @Column(nullable = false)
    private Date moodDate;

    @Column(nullable = false, length = 15)
    private String mood;

    public Integer getIdMood() {
        return idMood;
    }
    
    public void setIdMood(Integer idMood) {
        this.idMood = idMood;
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
