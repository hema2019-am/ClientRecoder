package com.example.app;

public class Model {
    public String PropertyName, Location, Locality, OwnerNumber, OwnerName, PreferedLanguage, ApplicationStatus, Comments;

    public Model(){}

    public Model(String propertyName, String location, String locality, String ownerNumber, String ownerName, String preferedLanguage, String applicationStatus, String comments) {
        PropertyName = propertyName;
        Location = location;
        Locality = locality;
        OwnerNumber = ownerNumber;
        OwnerName = ownerName;
        PreferedLanguage = preferedLanguage;
        ApplicationStatus = applicationStatus;
        Comments = comments;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getPropertyName() {
        return PropertyName;
    }

    public void setPropertyName(String propertyName) {
        PropertyName = propertyName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }

    public String getOwnerNumber() {
        return OwnerNumber;
    }

    public void setOwnerNumber(String ownerNumber) {
        OwnerNumber = ownerNumber;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public String getPreferedLanguage() {
        return PreferedLanguage;
    }

    public void setPreferedLanguage(String preferedLanguage) {
        PreferedLanguage = preferedLanguage;
    }

    public String getApplicationStatus() {
        return ApplicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        ApplicationStatus = applicationStatus;
    }
}
