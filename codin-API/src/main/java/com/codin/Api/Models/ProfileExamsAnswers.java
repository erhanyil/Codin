package com.codin.Api.Models;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@DynamicInsert
@Table(name = "profileExamsAnswers")
public class ProfileExamsAnswers implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    private Long profileID;

    private Long profileExamsID;

    private Long questionID;

    private String answer;

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

    public Long getProfileExamsID() {
        return profileExamsID;
    }

    public void setProfileExamsID(Long profileExamsID) {
        this.profileExamsID = profileExamsID;
    }

    public Long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Long questionID) {
        this.questionID = questionID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
