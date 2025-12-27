// Classe FormateurDAO : Gère les données du système
package dao;

import models.Formateur;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FormateurDAO {
    public boolean create(Formateur formateur) {
        String sql = "INSERT INTO formateurs (nom, prenom, email, telephone, specialite, annees_experience) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, formateur.getNom());
            pstmt.setString(2, formateur.getPrenom());
            pstmt.setString(3, formateur.getEmail());
            pstmt.setString(4, formateur.getTelephone());
            pstmt.setString(5, formateur.getSpecialite());
            pstmt.setInt(6, formateur.getAnneesExperience());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        formateur.setId(generatedKeys.getInt(1));
                        System.out.println("✓ Formateur créé avec succès: " + formateur.getPrenom() + " "
                                + formateur.getNom());
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Formateur findById(int id) {
        String sql = "SELECT * FROM formateurs WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToFormateur(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Formateur> findAll() {
        List<Formateur> list = new ArrayList<>();
        String sql = "SELECT * FROM formateurs";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToFormateur(rs));
            }
            System.out.println("✓ Trouvé " + list.size() + " formateur(s)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(Formateur formateur) {
        String sql = "UPDATE formateurs SET nom = ?, prenom = ?, email = ?, telephone = ?, specialite = ?, annees_experience = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, formateur.getNom());
            pstmt.setString(2, formateur.getPrenom());
            pstmt.setString(3, formateur.getEmail());
            pstmt.setString(4, formateur.getTelephone());
            pstmt.setString(5, formateur.getSpecialite());
            pstmt.setInt(6, formateur.getAnneesExperience());
            pstmt.setInt(7, formateur.getId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(
                        "✓ Formateur mis à jour avec succès: " + formateur.getPrenom() + " " + formateur.getNom());
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM formateurs WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✓ Formateur supprimé avec succès (ID: " + id + ")");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Formateur> findBySpecialite(String spec) {
        List<Formateur> list = new ArrayList<>();
        String sql = "SELECT * FROM formateurs WHERE specialite LIKE ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + spec + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToFormateur(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Formateur mapResultSetToFormateur(ResultSet rs) throws SQLException {
        Formateur f = new Formateur(
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("email"),
                rs.getString("telephone"),
                rs.getString("specialite"),
                rs.getInt("annees_experience"));
        f.setId(rs.getInt("id"));
        return f;
    }
}
// Fin de la classe FormateurDAO

