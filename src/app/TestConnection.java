// Classe TestConnection : Gère les données du système
package app;

import util.DatabaseConnection;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("Testing...");
        DatabaseConnection.getInstance();
    }
}
// Fin de la classe TestConnection

