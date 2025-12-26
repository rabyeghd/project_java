package dao;

import models.Contrainte;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContrainteDAO {
    public boolean create(Contrainte contrainte) {
        String sql = "INSERT INTO contraintes (type, description, entite_type, entite_id, priorite, active) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, contrainte.getType());
            pstmt.setString(2, contrainte.getDescription());
            pstmt.setString(3, contrainte.getEntiteType());
            pstmt.setInt(4, contrainte.getEntiteId());
            pstmt.setInt(5, contrainte.getPriorite());
            pstmt.setBoolean(6, contrainte.isActive());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        contrainte.setId(generatedKeys.getInt(1));
                        System.out.println("✓ Contrainte created successfully: " + contrainte.getType());
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Contrainte> findAll() {
        List<Contrainte> list = new ArrayList<>();
        String sql = "SELECT * FROM contraintes";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToContrainte(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM contraintes WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✓ Contrainte deleted successfully (ID: " + id + ")");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Contrainte mapResultSetToContrainte(ResultSet rs) throws SQLException {
        Contrainte c = new Contrainte(
                rs.getString("type"),
                rs.getString("description"),
                rs.getString("entite_type"),
                rs.getInt("entite_id"),
                rs.getInt("priorite"),
                rs.getBoolean("active"));
        c.setId(rs.getInt("id"));
        return c;
    }
}
