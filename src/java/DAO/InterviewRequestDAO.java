package DAO;

import models.InterviewRequest;
import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InterviewRequestDAO {

    public boolean requestInterview(int candidateId, int jobId, int slotId) {
        String sql = "INSERT INTO InterviewRequests (CandidateId, JobId, SlotId, Status, RequestedAt) VALUES (?, ?, ?, 'PENDING', CURRENT_TIMESTAMP)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, candidateId);
            stmt.setInt(2, jobId);
            stmt.setInt(3, slotId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<InterviewRequest> getRequestsForRecruiter(int recruiterId) {
        List<InterviewRequest> requests = new ArrayList<>();
        String sql = "SELECT ir.*" +
                     "FROM InterviewRequests ir " +
                     "JOIN Jobs j ON ir.JobId = j.Id " +
                     "WHERE j.RecruiterId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recruiterId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                requests.add(new InterviewRequest(
                    rs.getInt("Id"),
                    rs.getInt("CandidateId"),
                    rs.getInt("JobId"),
                    rs.getInt("SlotId"),
                    rs.getString("Status"),
                    rs.getTimestamp("RequestedAt")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requests;
    }

    public boolean approveRequest(int requestId, int slotId) {
        Connection conn = null;
        PreparedStatement updateRequestStmt = null;
        PreparedStatement updateSlotStmt = null;
        boolean success = false;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            String updateRequestSQL = "UPDATE InterviewRequests SET Status = 'CONFIRMED' WHERE Id = ?";
            updateRequestStmt = conn.prepareStatement(updateRequestSQL);
            updateRequestStmt.setInt(1, requestId);
            updateRequestStmt.executeUpdate();

            String updateSlotSQL = "UPDATE InterviewSlots SET Status = 'BOOKED' WHERE Id = ?";
            updateSlotStmt = conn.prepareStatement(updateSlotSQL);
            updateSlotStmt.setInt(1, slotId);
            updateSlotStmt.executeUpdate();

            conn.commit();
            success = true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (updateRequestStmt != null) updateRequestStmt.close();
                if (updateSlotStmt != null) updateSlotStmt.close();
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return success;
    }


    // ❌ Reject an interview request
    public boolean rejectRequest(int requestId) {
        String sql = "UPDATE InterviewRequests SET Status = 'DECLINED' WHERE Id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, requestId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<InterviewRequest> getRequestsForCandidate(int candidateId) {
        List<InterviewRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM InterviewRequests WHERE CandidateId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, candidateId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                requests.add(new InterviewRequest(
                    rs.getInt("Id"),
                    rs.getInt("CandidateId"),
                    rs.getInt("JobId"),
                    rs.getInt("SlotId"),
                    rs.getString("Status"),
                    rs.getTimestamp("RequestedAt")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requests;
    }
    
    public List<InterviewRequest> getApprovedRequestsForRecruiter(int recruiterId) {
        List<InterviewRequest> approvedRequests = new ArrayList<>();
        String sql = "SELECT * FROM InterviewRequests ir " +
                     "JOIN Jobs j ON ir.JobId = j.Id " +
                     "WHERE j.RecruiterId = ? AND ir.Status = 'CONFIRMED'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recruiterId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                approvedRequests.add(new InterviewRequest(
                    rs.getInt("Id"),
                    rs.getInt("CandidateId"),
                    rs.getInt("JobId"),
                    rs.getInt("SlotId"),
                    rs.getString("Status"),
                    rs.getTimestamp("RequestedAt")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return approvedRequests;
    }
    
    public List<InterviewRequest> getApprovedRequestsForCandidate(int candidateId) {
        List<InterviewRequest> approvedRequests = new ArrayList<>();
        String sql = "SELECT * FROM InterviewRequests WHERE CandidateId = ? AND Status = 'CONFIRMED'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, candidateId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                approvedRequests.add(new InterviewRequest(
                    rs.getInt("Id"),
                    rs.getInt("CandidateId"),
                    rs.getInt("JobId"),
                    rs.getInt("SlotId"),
                    rs.getString("Status"),
                    rs.getTimestamp("RequestedAt")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return approvedRequests;
    }
}
