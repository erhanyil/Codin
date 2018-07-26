package com.codin.Api.Models;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@DynamicInsert
@Table(name = "softwareLanguages")
public class SoftwareLanguages implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    private String softwareLanguage;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getSoftwareLanguage() {
        return softwareLanguage;
    }

    public void setSoftwareLanguage(String softwareLanguage) {
        this.softwareLanguage = softwareLanguage;
    }
}
