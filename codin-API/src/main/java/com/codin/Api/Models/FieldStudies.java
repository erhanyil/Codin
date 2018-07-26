package com.codin.Api.Models;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@DynamicInsert
@Table(name = "fieldStudies")
public class FieldStudies implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    private String fieldStudyName;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getFieldStudyName() {
        return fieldStudyName;
    }

    public void setFieldStudyName(String fieldStudyName) {
        this.fieldStudyName = fieldStudyName;
    }
}
