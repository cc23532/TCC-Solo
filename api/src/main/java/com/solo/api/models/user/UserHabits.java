package com.solo.api.models.user;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "UserHabits", schema = "appSolo")
public class UserHabits {

    @Id
    @Column(name = "idUser")
    private Integer idUser;

    @OneToOne
    @MapsId
    @JoinColumn(name = "idUser", referencedColumnName = "id")
    @JsonIgnore
    private SoloUser user;

    @Column(nullable = false)
    private boolean work;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time workBegin;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time workEnd;

    @Column(nullable = false)
    private boolean study;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time studyBegin;
    
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time studyEnd;
    
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time sleepBegin;
    
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time sleepEnd;

    @Column(nullable = false)
    private boolean workout;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time workoutBegin;
    
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time workoutEnd;

    @Column(nullable = false)
    private boolean smoke;

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

    public boolean isWork() {
        return work;
    }

    public void setWork(boolean work) {
        this.work = work;
    }

    public Time getWorkBegin() {
        return workBegin;
    }

    public void setWorkBegin(Time workBegin) {
        this.workBegin = workBegin;
    }

    public Time getWorkEnd() {
        return workEnd;
    }

    public void setWorkEnd(Time workEnd) {
        this.workEnd = workEnd;
    }

    public boolean isStudy() {
        return study;
    }

    public void setStudy(boolean study) {
        this.study = study;
    }

    public Time getStudyBegin() {
        return studyBegin;
    }

    public void setStudyBegin(Time studyBegin) {
        this.studyBegin = studyBegin;
    }

    public Time getStudyEnd() {
        return studyEnd;
    }

    public void setStudyEnd(Time studyEnd) {
        this.studyEnd = studyEnd;
    }

    public Time getSleepBegin() {
        return sleepBegin;
    }

    public void setSleepBegin(Time sleepBegin) {
        this.sleepBegin = sleepBegin;
    }

    public Time getSleepEnd() {
        return sleepEnd;
    }

    public void setSleepEnd(Time sleepEnd) {
        this.sleepEnd = sleepEnd;
    }

    public boolean isWorkout() {
        return workout;
    }

    public void setWorkout(boolean workout) {
        this.workout = workout;
    }

    public Time getWorkoutBegin() {
        return workoutBegin;
    }

    public void setWorkoutBegin(Time workoutBegin) {
        this.workoutBegin = workoutBegin;
    }

    public Time getWorkoutEnd() {
        return workoutEnd;
    }

    public void setWorkoutEnd(Time workoutEnd) {
        this.workoutEnd = workoutEnd;
    }

    public boolean isSmoke() {
        return smoke;
    }

    public void setSmoke(boolean smoke) {
        this.smoke = smoke;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserHabits that = (UserHabits) o;
        return work == that.work && study == that.study && workout == that.workout && smoke == that.smoke && Objects.equals(user, that.user) && Objects.equals(workBegin, that.workBegin) && Objects.equals(workEnd, that.workEnd) && Objects.equals(studyBegin, that.studyBegin) && Objects.equals(studyEnd, that.studyEnd) && Objects.equals(sleepBegin, that.sleepBegin) && Objects.equals(sleepEnd, that.sleepEnd) && Objects.equals(workoutBegin, that.workoutBegin) && Objects.equals(workoutEnd, that.workoutEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, work, workBegin, workEnd, study, studyBegin, studyEnd, sleepBegin, sleepEnd, workout, workoutBegin, workoutEnd, smoke);
    }

    @Override
    public String toString() {
        return "UserHabits{" +
                "user=" + user.getId() +
                ", work=" + work +
                ", workBegin=" + workBegin +
                ", workEnd=" + workEnd +
                ", study=" + study +
                ", studyBegin=" + studyBegin +
                ", studyEnd=" + studyEnd +
                ", sleepBegin=" + sleepBegin +
                ", sleepEnd=" + sleepEnd +
                ", workout=" + workout +
                ", workoutBegin=" + workoutBegin +
                ", workoutEnd=" + workoutEnd +
                ", smoke=" + smoke +
                '}';
    }
}
