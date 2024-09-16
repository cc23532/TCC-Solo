package com.solo.api.models.workout;

import java.sql.Time;
import java.time.LocalDateTime;

import com.solo.api.models.user.SoloUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "MuscleActivity", schema = "appSolo")
public class MuscleActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idActivity;

    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private SoloUser user;

    @Column(nullable = false)
    private LocalDateTime activityDate;

    @Column(nullable = false)
    private Time duration;

    public MuscleActivity(){

    }

    public MuscleActivity(Integer idActivity, SoloUser user, LocalDateTime activityDate, Time duration){
        this.idActivity = idActivity;
        this.user = user;
        this.activityDate = activityDate;
        this.duration = duration;
    }

    public Integer getIdActivity() {
        return idActivity;
    }

    public SoloUser getUser() {
        return user;
    }

    public void setUser(SoloUser user) {
        this.user = user;
    }

    public LocalDateTime getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDateTime activityDate) {
        this.activityDate = activityDate;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }


}
