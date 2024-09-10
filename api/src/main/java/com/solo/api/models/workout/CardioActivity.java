package com.solo.api.models.workout;

import com.solo.api.models.user.SoloUser;
import jakarta.persistence.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "CardioActivity",schema = "appSolo")
public class CardioActivity {

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

    @Column(nullable = false)
    private double distance;

    @Column(nullable = false)
    private double averageSpeed;

    @Column(nullable = false)
    private double lostKCal;

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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public double getLostKCal() {
        return lostKCal;
    }

    public void setLostKCal(double lostKCal) {
        this.lostKCal = lostKCal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardioActivity that = (CardioActivity) o;
        return Double.compare(distance, that.distance) == 0 && Double.compare(averageSpeed, that.averageSpeed) == 0 && Double.compare(lostKCal, that.lostKCal) == 0 && Objects.equals(idActivity, that.idActivity) && Objects.equals(user, that.user) && Objects.equals(activityDate, that.activityDate) && Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idActivity, user, activityDate, duration, distance, averageSpeed, lostKCal);
    }

    @Override
    public String toString() {
        return "CardioActivity{" +
                "idActivity=" + idActivity +
                ", user=" + user +
                ", activityDate=" + activityDate +
                ", duration=" + duration +
                ", distance=" + distance +
                ", averageSpeed=" + averageSpeed +
                ", lostKCal=" + lostKCal +
                '}';
    }
}
