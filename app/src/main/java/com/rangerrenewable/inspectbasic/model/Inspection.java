package com.rangerrenewable.inspectbasic.model;

import java.util.List;
import java.util.Map;

/**
 * Inspection
 *  - Simple Pass/Fail plus other fields.
 */
public class Inspection {

    private String defectCode;
    private String title;
    private String description;

    // any photos referenced for this inspection
    private List<InspectionPhoto> photos;

    // boolean pass/fail
    private boolean response;

    private String result;

    private Map<String, String> values;

    private String comments;

    public Inspection(String defectCode,
                      String title,
                      String description,
                      List<InspectionPhoto> photos,
                      boolean response,
                      String result,
                      Map<String, String> values,
                      String comments) {
        this.defectCode = defectCode;
        this.title = title;
        this.description = description;
        this.photos = photos;
        this.response = response;
        this.result = result;
        this.values = values;
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<InspectionPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<InspectionPhoto> photos) {
        this.photos = photos;
    }

    public boolean getResponse() {
        return response;
    }

    // accessor for setting an inspection as passed or failed
    public void setResponse(boolean response) {
        this.response = response;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
