package models;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Represents an interview slot that a recruiter creates and a candidate can book.
 */
public class InterviewSlot implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int recruiterId;
    private Integer candidateId;  // Nullable (NULL when the slot is not booked)
    private int jobId;
    private Timestamp dateTime;
    private String status;  // Possible values: AVAILABLE, BOOKED, COMPLETED

    // ✅ Default Constructor
    public InterviewSlot() {}

    // ✅ Constructor for Creating a Slot (Recruiter sets availability, no candidate assigned)
    public InterviewSlot(int recruiterId, int jobId, Timestamp dateTime) {
        this.recruiterId = recruiterId;
        this.jobId = jobId;
        this.dateTime = dateTime;
        this.status = "AVAILABLE";  // Default status
        this.candidateId = null; // Slot starts unassigned
    }

    // ✅ Constructor for Retrieving an Existing Slot (Including candidate)
    public InterviewSlot(int id, int recruiterId, Integer candidateId, int jobId, Timestamp dateTime, String status) {
        this.id = id;
        this.recruiterId = recruiterId;
        this.candidateId = candidateId;
        this.jobId = jobId;
        this.dateTime = dateTime;
        this.status = status;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRecruiterId() { return recruiterId; }
    public void setRecruiterId(int recruiterId) { this.recruiterId = recruiterId; }

    public Integer getCandidateId() { return candidateId; }
    public void setCandidateId(Integer candidateId) { this.candidateId = candidateId; }

    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }

    public Timestamp getDateTime() { return dateTime; }
    public void setDateTime(Timestamp dateTime) { this.dateTime = dateTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "InterviewSlot{" +
               "id=" + id +
               ", recruiterId=" + recruiterId +
               ", candidateId=" + candidateId +
               ", jobId=" + jobId +
               ", dateTime=" + dateTime +
               ", status='" + status + '\'' +
               '}';
    }
}