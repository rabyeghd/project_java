// Classe EtudiantDAO : Gère les données du système
package dao;

import models.Etudiant;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO {
    public boolean create(Etudiant etudiant) {
        String sql = "INSERT INTO etudiants (nom, prenom, email, telephone, niveau, date_inscription) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, etudiant.getNom());
            pstmt.setString(2, etudiant.getPrenom());
            pstmt.setString(3, etudiant.getEmail());
            pstmt.setString(4, etudiant.getTelephone());
            pstmt.setString(5, etudiant.getNiveau());
            pstmt.setDate(6, etudiant.getDateInscription());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        etudiant.setId(generatedKeys.getInt(1));
                        System.out.println(
                                "✓ Etudiant créé avec succès: " + etudiant.getPrenom() + " " + etudiant.getNom());
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Etudiant findById(int id) {
        String sql = "SELECT * FROM etudiants WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEtudiant(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Etudiant> findAll() {
        List<Etudiant> list = new ArrayList<>();
        String sql = "SELECT * FROM etudiants";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToEtudiant(rs));
            }
            System.out.println("✓ Trouvé " + list.size() + " etudiant(s)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(Etudiant etudiant) {
        String sql = "UPDATE etudiants SET nom = ?, prenom = ?, email = ?, telephone = ?, niveau = ?, date_inscription = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, etudiant.getNom());
            pstmt.setString(2, etudiant.getPrenom());
            pstmt.setString(3, etudiant.getEmail());
            pstmt.setString(4, etudiant.getTelephone());
            pstmt.setString(5, etudiant.getNiveau());
            pstmt.setDate(6, etudiant.getDateInscription());
            pstmt.setInt(7, etudiant.getId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out
                        .println("✓ Etudiant mis à jour avec succès: " + etudiant.getPrenom() + " " + etudiant.getNom());
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM etudiants WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✓ Etudiant supprimé avec succès (ID: " + id + ")");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Etudiant> findByNiveau(String niveau) {
        List<Etudiant> list = new ArrayList<>();
        String sql = "SELECT * FROM etudiants WHERE niveau = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, niveau);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToEtudiant(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Etudiant mapResultSetToEtudiant(ResultSet rs) throws SQLException {
        Etudiant e = new Etudiant(
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("email"),
                rs.getString("telephone"),
                rs.getString("niveau"),
                rs.getDate("date_inscription"));
        e.setId(rs.getInt("id"));
        return e;
    }
}
// Fin de la classe EtudiantDAO

