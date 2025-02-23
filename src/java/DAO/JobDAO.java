package DAO;

import models.Job;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import utils.DatabaseConnection;

public class JobDAO {

    public boolean addJob(Job job) {
        String sql = "INSERT INTO Jobs (RecruiterId, Title, Description, Location, Industry) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, job.getRecruiterId());
            stmt.setString(2, job.getTitle());
            stmt.setString(3, job.getDescription());
            stmt.setString(4, job.getLocation());
            stmt.setString(5, job.getIndustry());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Job> getAllJobs() {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM Jobs";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                jobs.add(new Job(
                    rs.getInt("Id"),
                    rs.getInt("RecruiterId"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Industry")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobs;
    }

    public List<Job> getJobsByRecruiter(int recruiterId) {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM Jobs WHERE RecruiterId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recruiterId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                jobs.add(new Job(
                    rs.getInt("Id"),
                    rs.getInt("RecruiterId"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Industry")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobs;
    }

    public boolean deleteJob(int jobId) {
        String sql = "DELETE FROM Jobs WHERE Id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, jobId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Job getJobById(int jobId) {
        String sql = "SELECT * FROM Jobs WHERE Id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, jobId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Job(
                    rs.getInt("Id"),
                    rs.getInt("RecruiterId"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Industry")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public List<Job> searchJobs(String keyword) {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM Jobs WHERE Title LIKE ? OR Description LIKE ? OR Location LIKE ? OR Industry LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            stmt.setString(4, searchPattern);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                jobs.add(new Job(
                    rs.getInt("Id"),
                    rs.getInt("RecruiterId"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Industry")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobs;
    }
    
    public Set<String> getAllLocations() {
        Set<String> locations = new HashSet<>();
        String sql = "SELECT DISTINCT Location FROM Jobs WHERE Location IS NOT NULL";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                locations.add(rs.getString("Location"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }

    public Set<String> getAllIndustries() {
        Set<String> industries = new HashSet<>();
        String sql = "SELECT DISTINCT Industry FROM Jobs WHERE Industry IS NOT NULL";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                industries.add(rs.getString("Industry"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return industries;
    }
    
    public List<Job> searchJobs(String keyword, String location, String industry) {
        List<Job> jobs = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Jobs WHERE 1=1");

        List<String> params = new ArrayList<>();

        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND (Title LIKE ? OR Description LIKE ?)");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
        }

        if (location != null && !location.isEmpty()) {
            sql.append(" AND Location = ?");
            params.add(location);
        }

        if (industry != null && !industry.isEmpty()) {
            sql.append(" AND Industry = ?");
            params.add(industry);
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setString(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                jobs.add(new Job(
                    rs.getInt("Id"),
                    rs.getInt("RecruiterId"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Industry")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobs;
    }
    
}