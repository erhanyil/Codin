package com.codin.Api.Models;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@DynamicInsert
@Table(name = "profileEducations")
public class ProfileEducations implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    private Long profileID;

    private Long universityID;

    private Long degreeID;

    private Long fieldStudyID;

    private String grade;

    private Date startDate;

    private Date endDate;

    private String activities;

    private String description;

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

    public Long getUniversityID() {
        return universityID;
    }

    public void setUniversityID(Long universityID) {
        this.universityID = universityID;
    }

    public Long getDegreeID() {
        return degreeID;
    }

    public void setDegreeID(Long degreeID) {
        this.degreeID = degreeID;
    }

    public Long getFieldStudyID() {
        return fieldStudyID;
    }

    public void setFieldStudyID(Long fieldStudyID) {
        this.fieldStudyID = fieldStudyID;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
