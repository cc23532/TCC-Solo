package com.solo.api.models;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "UserHabits", schema = "appSolo")
public class UserHabits {

    @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    private SoloUser user;

    @Column(nullable = false)
    private boolean work;

    @Column
    private Time workBegin;

    @Column
    private Time workEnd;

    @Column(nullable = false)
    private boolean study;

    @Column
    private Time studyBegin;

    @Column
    private Time studyEnd;

    @Column (nullable = false)
    private Time sleepBegin;

    @Column(nullable = false)
    private Time sleepEnd;

    @Column(nullable = false)
    private boolean workout;

    @Column
    private Time workoutBegin;

    @Column
    private Time workoutEnd;

    @Column(nullable = false)
    private boolean smoke;

    public SoloUser getUser() {
        return user;
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
                "user=" + user +
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
