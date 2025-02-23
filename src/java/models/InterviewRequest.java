/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Timestamp;
/**
 *
 * @author nhl08
 */
public class InterviewRequest {
    private int id;
    private int candidateId;
    private int jobId;
    private int slotId;
    private String status;
    private Timestamp requestedAt;

    // Constructor
    public InterviewRequest(int id, int candidateId, int jobId, int slotId, String status, Timestamp requestedAt) {
        this.id = id;
        this.candidateId = candidateId;
        this.jobId = jobId;
        this.slotId = slotId;
        this.status = status;
        this.requestedAt = requestedAt;
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
     * @return the candidateId
     */
    public int getCandidateId() {
        return candidateId;
    }

    /**
     * @param candidateId the candidateId to set
     */
    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    /**
     * @return the jobId
     */
    public int getJobId() {
        return jobId;
    }

    /**
     * @param jobId the jobId to set
     */
    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    /**
     * @return the slotId
     */
    public int getSlotId() {
        return slotId;
    }

    /**
     * @param slotId the slotId to set
     */
    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the requestedAt
     */
    public Timestamp getRequestedAt() {
        return requestedAt;
    }

    /**
     * @param requestedAt the requestedAt to set
     */
    public void setRequestedAt(Timestamp requestedAt) {
        this.requestedAt = requestedAt;
    }
    
    
}
