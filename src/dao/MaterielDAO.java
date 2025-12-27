// Classe MaterielDAO : Gère les données du système
package dao;

import models.Materiel;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterielDAO {
    public boolean create(Materiel materiel) {
        String sql = "INSERT INTO materiel (nom, type, quantite_disponible, quantite_totale, etat, description) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, materiel.getNom());
            pstmt.setString(2, materiel.getType());
            pstmt.setInt(3, materiel.getQuantiteDisponible());
            pstmt.setInt(4, materiel.getQuantiteTotale());
            pstmt.setString(5, materiel.getEtat());
            pstmt.setString(6, materiel.getDescription());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        materiel.setId(generatedKeys.getInt(1));
                        System.out.println("✓ Materiel créé avec succès: " + materiel.getNom());
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Materiel findById(int id) {
        String sql = "SELECT * FROM materiel WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMateriel(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Materiel> findAll() {
        List<Materiel> list = new ArrayList<>();
        String sql = "SELECT * FROM materiel";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToMateriel(rs));
            }
            System.out.println("✓ Trouvé " + list.size() + " materiel(s)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(Materiel materiel) {
        String sql = "UPDATE materiel SET nom = ?, type = ?, quantite_disponible = ?, quantite_totale = ?, etat = ?, description = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, materiel.getNom());
            pstmt.setString(2, materiel.getType());
            pstmt.setInt(3, materiel.getQuantiteDisponible());
            pstmt.setInt(4, materiel.getQuantiteTotale());
            pstmt.setString(5, materiel.getEtat());
            pstmt.setString(6, materiel.getDescription());
            pstmt.setInt(7, materiel.getId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✓ Materiel mis à jour avec succès: " + materiel.getNom());
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM materiel WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✓ Materiel supprimé avec succès (ID: " + id + ")");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Materiel> findByType(String type) {
        List<Materiel> list = new ArrayList<>();
        String sql = "SELECT * FROM materiel WHERE type = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToMateriel(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Materiel mapResultSetToMateriel(ResultSet rs) throws SQLException {
        Materiel m = new Materiel(
                rs.getString("nom"),
                rs.getString("type"),
                rs.getInt("quantite_disponible"),
                rs.getInt("quantite_totale"),
                rs.getString("etat"),
                rs.getString("description"));
        m.setId(rs.getInt("id"));
        return m;
    }
}
// Fin de la classe MaterielDAO

