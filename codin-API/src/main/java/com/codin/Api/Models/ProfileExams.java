package com.codin.Api.Models;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@DynamicInsert
@Table(name = "profileExams")
public class ProfileExams implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    private Long profileID;

    private Long softwareLanguageID;

    private Long softwareLanguageLevel;

    private Long status;

    private Long attempt;

    private Long duration;

    private Date date;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getProfileID() {
        return profileID;
    }

    public void setProfileID(Long profileID) {
        this.profileID = profileID;
    }

    public Long getSoftwareLanguageID() {
        return softwareLanguageID;
    }

    public void setSoftwareLanguageID(Long softwareLanguageID) {
        this.softwareLanguageID = softwareLanguageID;
    }

    public Long getSoftwareLanguageLevel() {
        return softwareLanguageLevel;
    }

    public void setSoftwareLanguageLevel(Long softwareLanguageLevel) {
        this.softwareLanguageLevel = softwareLanguageLevel;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getAttempt() {
        return attempt;
    }

    public void setAttempt(Long attempt) {
        this.attempt = attempt;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
