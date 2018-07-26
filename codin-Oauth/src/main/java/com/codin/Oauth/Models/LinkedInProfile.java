package com.codin.Oauth.Models;

import java.io.Serializable;
import java.util.List;

public class LinkedInProfile implements Serializable {
    private static final long serialVersionUID = -6899282077754418300L;

    private String id;
    private String firstName;
    private String lastName;
    private String headline;
    private String emailAddress;
    private String industry;
    private String pictureUrl;
    private PictureUrls pictureUrls;
    private Location location;
    private Positions positions;
    private String summary;
    private String formattedName;
    private Integer numConnections;
    private Boolean numConnectionsCapped;
    private String publicProfileUrl;

    public PictureUrls getPictureUrls() {
        return pictureUrls;
    }

    public void setPictureUrls(PictureUrls pictureUrls) {
        this.pictureUrls = pictureUrls;
    }

    public String getPictureUrlsFirst() {
        return pictureUrls.getValues().get(0);
    }

    public String getPublicProfileUrl() {
        return publicProfileUrl;
    }

    public void setPublicProfileUrl(String publicProfileUrl) {
        this.publicProfileUrl = publicProfileUrl;
    }

    public Boolean getNumConnectionsCapped() {
        return numConnectionsCapped;
    }

    public void setNumConnectionsCapped(Boolean numConnectionsCapped) {
        this.numConnectionsCapped = numConnectionsCapped;
    }

    public Integer getNumConnections() {
        return numConnections;
    }

    public void setNumConnections(Integer numConnections) {
        this.numConnections = numConnections;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public void setFormattedName(String formattedName) {
        this.formattedName = formattedName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Positions getPositions() {
        return positions;
    }

    public void setPositions(Positions positions) {
        this.positions = positions;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}

class Positions implements Serializable {
    private Integer _total;
    private List<Values> values;

    public List<Values> getValues() {
        return values;
    }

    public void setValues(List<Values> values) {
        this.values = values;
    }

    public Integer get_total() {
        return _total;
    }

    public void set_total(Integer _total) {
        this._total = _total;
    }
}

class Values implements Serializable {
    private Integer id;
    private String title;
    private String summary;
    private StartDate startDate;
    private EndDate endDate;
    private Boolean isCurrent;
    private Company company;
    private Location location;

    public EndDate getEndDate() {
        return endDate;
    }

    public void setEndDate(EndDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getCurrent() {
        return isCurrent;
    }

    public void setCurrent(Boolean current) {
        isCurrent = current;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public StartDate getStartDate() {
        return startDate;
    }

    public void setStartDate(StartDate startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

class Company implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class StartDate implements Serializable {
    private Integer month;
    private Integer year;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}

class EndDate implements Serializable {
    private Integer month;
    private Integer year;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}

class Location implements Serializable {
    private Country country;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}

class Country implements Serializable {
    private String code;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

class PictureUrls implements Serializable {
    private Integer _total;
    private List<String> values;

    public Integer get_total() {
        return _total;
    }

    public void set_total(Integer _total) {
        this._total = _total;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}