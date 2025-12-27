// Classe DisponibiliteDAO : Gère les données du système
package dao;

import models.Disponibilite;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisponibiliteDAO {
    public boolean create(Disponibilite disp) {
        String sql = "INSERT INTO disponibilites (formateur_id, jour_semaine, heure_debut, heure_fin, date_debut, date_fin, type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, disp.getFormateurId());
            pstmt.setString(2, disp.getJourSemaine());
            pstmt.setTime(3, disp.getHeureDebut());
            pstmt.setTime(4, disp.getHeureFin());
            pstmt.setDate(5, disp.getDateDebut());
            pstmt.setDate(6, disp.getDateFin());
            pstmt.setString(7, disp.getType());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        disp.setId(generatedKeys.getInt(1));
                        System.out.println(
                                "✓ Disponibilite créé avec succès for formateur ID: " + disp.getFormateurId());
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Disponibilite> findByFormateurId(int id) {
        List<Disponibilite> list = new ArrayList<>();
        String sql = "SELECT * FROM disponibilites WHERE formateur_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToDisponibilite(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Disponibilite> findAll() {
        List<Disponibilite> list = new ArrayList<>();
        String sql = "SELECT * FROM disponibilites";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToDisponibilite(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(Disponibilite disp) {
        String sql = "UPDATE disponibilites SET jour_semaine = ?, heure_debut = ?, heure_fin = ?, date_debut = ?, date_fin = ?, type = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, disp.getJourSemaine());
            pstmt.setTime(2, disp.getHeureDebut());
            pstmt.setTime(3, disp.getHeureFin());
            pstmt.setDate(4, disp.getDateDebut());
            pstmt.setDate(5, disp.getDateFin());
            pstmt.setString(6, disp.getType());
            pstmt.setInt(7, disp.getId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✓ Disponibilite mis à jour avec succès (ID: " + disp.getId() + ")");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM disponibilites WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✓ Disponibilite supprimé avec succès (ID: " + id + ")");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Disponibilite mapResultSetToDisponibilite(ResultSet rs) throws SQLException {
        Disponibilite d = new Disponibilite(
                rs.getInt("formateur_id"),
                rs.getString("jour_semaine"),
                rs.getTime("heure_debut"),
                rs.getTime("heure_fin"),
                rs.getDate("date_debut"),
                rs.getDate("date_fin"),
                rs.getString("type"));
        d.setId(rs.getInt("id"));
        return d;
    }
}
// Fin de la classe DisponibiliteDAO

