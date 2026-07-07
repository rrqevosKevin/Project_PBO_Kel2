package Model;

import java.sql.Connection;
import java.sql.SQLException;

public class TestKoneksi {
    public static void main(String[] args) {
        try {
            Connection conn = Connector.configDB();
            if (conn != null) {
                System.out.println("Berhasil! Koneksi stabil.");
            }
        } catch (Exception e) {
            System.out.println("Gagal total: " + e.getMessage());
        }
    }
}