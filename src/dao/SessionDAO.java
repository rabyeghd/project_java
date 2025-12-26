package dao;

import models.Session;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class SessionDAO {
    public boolean create(Session session) {
        String sql = "INSERT INTO sessions (nom_cours, formateur_id, salle_id, date_debut, date_fin, duree_heures, nombre_etudiants, statut, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, session.getNomCours());
            pstmt.setInt(2, session.getFormateurId());
            pstmt.setInt(3, session.getSalleId());
            pstmt.setTimestamp(4, session.getDateDebut());
            pstmt.setTimestamp(5, session.getDateFin());
            pstmt.setBigDecimal(6, session.getDureeHeures());
            pstmt.setInt(7, session.getNombreEtudiants());
            pstmt.setString(8, session.getStatut());
            pstmt.setString(9, session.getDescription());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        session.setId(generatedKeys.getInt(1));
                        System.out.println("✓ Session created successfully: " + session.getNomCours());
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Session findById(int id) {
        String sql = "SELECT * FROM sessions WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToSession(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Session> findAll() {
        List<Session> list = new ArrayList<>();
        String sql = "SELECT * FROM sessions";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToSession(rs));
            }
            System.out.println("✓ Found " + list.size() + " session(s)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(Session session) {
        String sql = "UPDATE sessions SET nom_cours = ?, formateur_id = ?, salle_id = ?, date_debut = ?, date_fin = ?, duree_heures = ?, nombre_etudiants = ?, statut = ?, description = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, session.getNomCours());
            pstmt.setInt(2, session.getFormateurId());
            pstmt.setInt(3, session.getSalleId());
            pstmt.setTimestamp(4, session.getDateDebut());
            pstmt.setTimestamp(5, session.getDateFin());
            pstmt.setBigDecimal(6, session.getDureeHeures());
            pstmt.setInt(7, session.getNombreEtudiants());
            pstmt.setString(8, session.getStatut());
            pstmt.setString(9, session.getDescription());
            pstmt.setInt(10, session.getId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✓ Session updated successfully: " + session.getNomCours());
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM sessions WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✓ Session deleted successfully (ID: " + id + ")");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Session> findByStatut(String statut) {
        List<Session> list = new ArrayList<>();
        String sql = "SELECT * FROM sessions WHERE statut = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, statut);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToSession(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Session mapResultSetToSession(ResultSet rs) throws SQLException {
        Session s = new Session(
                rs.getString("nom_cours"),
                rs.getInt("formateur_id"),
                rs.getInt("salle_id"),
                rs.getTimestamp("date_debut"),
                rs.getTimestamp("date_fin"),
                rs.getBigDecimal("duree_heures"),
                rs.getInt("nombre_etudiants"),
                rs.getString("statut"),
                rs.getString("description"));
        s.setId(rs.getInt("id"));
        return s;
    }
}
