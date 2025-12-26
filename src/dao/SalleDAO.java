package dao;

import models.Salle;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalleDAO {
    public boolean create(Salle salle) {
        String sql = "INSERT INTO salles (nom, capacite, type, equipements_fixes, localisation, disponible) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, salle.getNom());
            pstmt.setInt(2, salle.getCapacite());
            pstmt.setString(3, salle.getType());
            pstmt.setString(4, salle.getEquipementsFixes());
            pstmt.setString(5, salle.getLocalisation());
            pstmt.setBoolean(6, salle.isDisponible());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        salle.setId(generatedKeys.getInt(1));
                        System.out.println("✓ Salle created successfully: " + salle.getNom());
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Salle findById(int id) {
        String sql = "SELECT * FROM salles WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToSalle(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Salle> findAll() {
        List<Salle> list = new ArrayList<>();
        String sql = "SELECT * FROM salles";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToSalle(rs));
            }
            System.out.println("✓ Found " + list.size() + " salle(s)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(Salle salle) {
        String sql = "UPDATE salles SET nom = ?, capacite = ?, type = ?, equipements_fixes = ?, localisation = ?, disponible = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, salle.getNom());
            pstmt.setInt(2, salle.getCapacite());
            pstmt.setString(3, salle.getType());
            pstmt.setString(4, salle.getEquipementsFixes());
            pstmt.setString(5, salle.getLocalisation());
            pstmt.setBoolean(6, salle.isDisponible());
            pstmt.setInt(7, salle.getId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✓ Salle updated successfully: " + salle.getNom());
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM salles WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✓ Salle deleted successfully (ID: " + id + ")");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Salle> findByType(String type) {
        List<Salle> list = new ArrayList<>();
        String sql = "SELECT * FROM salles WHERE type = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToSalle(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Salle mapResultSetToSalle(ResultSet rs) throws SQLException {
        Salle s = new Salle(
                rs.getString("nom"),
                rs.getInt("capacite"),
                rs.getString("type"),
                rs.getString("equipements_fixes"),
                rs.getString("localisation"),
                rs.getBoolean("disponible"));
        s.setId(rs.getInt("id"));
        return s;
    }
}
