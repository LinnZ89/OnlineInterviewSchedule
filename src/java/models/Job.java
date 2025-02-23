package models;

import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nhl08
 */
public class Job implements Serializable {
    private int id;
    private int recruiterId;
    private String title;
    private String description;
    private String location;
    private String industry;
    
    public Job() {}
    
    public Job(int recruiterId, String title, String description, String location, String industry) {
        this.recruiterId = recruiterId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.industry = industry;
    }
    
    public Job(int id, int recruiterId, String title, String description, String location, String industry) {
        this.id = id;
        this.recruiterId = recruiterId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.industry = industry;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the recruiterId
     */
    public int getRecruiterId() {
        return recruiterId;
    }

    /**
     * @param recruiterId the recruiterId to set
     */
    public void setRecruiterId(int recruiterId) {
        this.recruiterId = recruiterId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the industry
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * @param industry the industry to set
     */
    public void setIndustry(String industry) {
        this.industry = industry;
    }
    
    
}
