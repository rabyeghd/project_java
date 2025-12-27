// Classe PreferenceDAO : Gère les données du système
package dao;

import models.Preference;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreferenceDAO {
    public boolean create(Preference pref) {
        String sql = "INSERT INTO preferences (entite_type, entite_id, type_preference, valeur, priorite) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, pref.getEntiteType());
            pstmt.setInt(2, pref.getEntiteId());
            pstmt.setString(3, pref.getTypePreference());
            pstmt.setString(4, pref.getValeur());
            pstmt.setInt(5, pref.getPriorite());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        pref.setId(generatedKeys.getInt(1));
                        System.out.println("✓ Preference créé avec succès: " + pref.getTypePreference());
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Preference> findByEntite(String type, int id) {
        List<Preference> list = new ArrayList<>();
        String sql = "SELECT * FROM preferences WHERE entite_type = ? AND entite_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type);
            pstmt.setInt(2, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToPreference(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Preference> findAll() {
        List<Preference> list = new ArrayList<>();
        String sql = "SELECT * FROM preferences";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToPreference(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(Preference pref) {
        String sql = "UPDATE preferences SET type_preference = ?, valeur = ?, priorite = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pref.getTypePreference());
            pstmt.setString(2, pref.getValeur());
            pstmt.setInt(3, pref.getPriorite());
            pstmt.setInt(4, pref.getId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✓ Preference mis à jour avec succès: " + pref.getTypePreference());
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM preferences WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✓ Preference supprimé avec succès (ID: " + id + ")");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Preference mapResultSetToPreference(ResultSet rs) throws SQLException {
        Preference p = new Preference(
                rs.getString("entite_type"),
                rs.getInt("entite_id"),
                rs.getString("type_preference"),
                rs.getString("valeur"),
                rs.getInt("priorite"));
        p.setId(rs.getInt("id"));
        return p;
    }
}
// Fin de la classe PreferenceDAO

