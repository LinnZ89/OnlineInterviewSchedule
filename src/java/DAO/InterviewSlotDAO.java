package DAO;

import models.InterviewSlot;
import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InterviewSlotDAO {

    public boolean addInterviewSlot(InterviewSlot slot) {
        String sql = "INSERT INTO InterviewSlots (RecruiterId, JobId, DateTime, Status) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, slot.getRecruiterId());
            stmt.setInt(2, slot.getJobId());
            stmt.setTimestamp(3, slot.getDateTime());
            stmt.setString(4, "AVAILABLE");

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<InterviewSlot> getAvailableSlots() {
        List<InterviewSlot> slots = new ArrayList<>();
        String sql = "SELECT * FROM InterviewSlots WHERE Status = 'AVAILABLE'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                slots.add(new InterviewSlot(
                    rs.getInt("Id"),
                    rs.getInt("RecruiterId"),
                    null,
                    rs.getInt("JobId"),
                    rs.getTimestamp("DateTime"),
                    rs.getString("Status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slots;
    }

    public boolean bookSlot(int slotId, int candidateId) {
        String sql = "UPDATE InterviewSlots SET CandidateId = ?, Status = 'BOOKED' WHERE Id = ? AND Status = 'AVAILABLE'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, candidateId);
            stmt.setInt(2, slotId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<InterviewSlot> getSlotsByRecruiter(int recruiterId) {
        List<InterviewSlot> slots = new ArrayList<>();
        String sql = "SELECT * FROM InterviewSlots WHERE RecruiterId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recruiterId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                slots.add(new InterviewSlot(
                    rs.getInt("Id"),
                    rs.getInt("RecruiterId"),
                    rs.getInt("CandidateId"),
                    rs.getInt("JobId"),
                    rs.getTimestamp("DateTime"),
                    rs.getString("Status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slots;
    }

    public List<InterviewSlot> getBookedSlotsForRecruiter(int recruiterId) {
        List<InterviewSlot> slots = new ArrayList<>();
        String sql = "SELECT * FROM InterviewSlots WHERE RecruiterId = ? AND Status = 'BOOKED'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recruiterId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                slots.add(new InterviewSlot(
                    rs.getInt("Id"),
                    rs.getInt("RecruiterId"),
                    rs.getInt("CandidateId"),
                    rs.getInt("JobId"),
                    rs.getTimestamp("DateTime"),
                    rs.getString("Status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slots;
    }

    public boolean updateSlotStatus(int slotId, String newStatus) {
        String sql = "UPDATE InterviewSlots SET Status = ? WHERE Id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, slotId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<InterviewSlot> getSlotsForCandidate(int candidateId) {
        List<InterviewSlot> slots = new ArrayList<>();
        String sql = "SELECT * FROM InterviewSlots WHERE CandidateId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, candidateId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                slots.add(new InterviewSlot(
                    rs.getInt("Id"),
                    rs.getInt("RecruiterId"),
                    rs.getInt("CandidateId"),
                    rs.getInt("JobId"),
                    rs.getTimestamp("DateTime"),
                    rs.getString("Status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slots;
    }
    
    public List<InterviewSlot> getAvailableSlotsForJob(int jobId, int candidateId) {
        List<InterviewSlot> slots = new ArrayList<>();
        String sql = "SELECT * FROM InterviewSlots s " +
                     "WHERE s.JobId = ? AND s.Status = 'AVAILABLE' " +
                     "AND NOT EXISTS (SELECT 1 FROM InterviewRequests r WHERE r.SlotId = s.Id AND r.CandidateId = ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, jobId);
            stmt.setInt(2, candidateId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                slots.add(new InterviewSlot(
                    rs.getInt("Id"),
                    rs.getInt("RecruiterId"),
                    null,
                    rs.getInt("JobId"),
                    rs.getTimestamp("DateTime"),
                    rs.getString("Status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slots;
    }
    
    public List<InterviewSlot> getApprovedSlotsForRecruiter(int recruiterId) {
        List<InterviewSlot> slots = new ArrayList<>();
        String sql = "SELECT * FROM InterviewSlots WHERE RecruiterId = ? AND Status = 'CONFIRMED'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recruiterId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                slots.add(new InterviewSlot(
                    rs.getInt("Id"),
                    rs.getInt("RecruiterId"),
                    rs.getInt("CandidateId"),
                    rs.getInt("JobId"),
                    rs.getTimestamp("DateTime"),
                    rs.getString("Status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slots;
    }

}
