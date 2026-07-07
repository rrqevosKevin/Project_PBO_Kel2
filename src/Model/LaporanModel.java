package Model;

import java.util.*;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.Timestamp;

public class LaporanModel implements PenghapusData {

    public int getId_laporan() {
        return id_laporan;
    }

    public void setId_laporan(int id_laporan) {
        this.id_laporan = id_laporan;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getJenis_kerusakan() {
        return jenis_kerusakan;
    }

    public void setJenis_kerusakan(String jenis_kerusakan) {
        this.jenis_kerusakan = jenis_kerusakan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
    
    private int id_laporan;
    private int id_user;
    private String judul;
    private String lokasi;
    private String deskripsi;
    private String jenis_kerusakan;
    private String status;
    private Timestamp created_at;
    
    public boolean simpanLaporan(LaporanModel data) throws SQLException {
        PreparedStatement pstm = null;
        Connection conn = (Connection) Connector.configDB();
        String sql = "INSERT INTO laporan (id_user, judul, lokasi, deskripsi, jenis_kerusakan) VALUES (?, ?, ?, ?, ?)";
        
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, data.getId_user());
            pstm.setString(2, data.getJudul());
            pstm.setString(3, data.getLokasi());
            pstm.setString(4, data.getDeskripsi());
            pstm.setString(5, data.getJenis_kerusakan());
            pstm.executeUpdate();
            return true;
        } catch (HeadlessException | SQLException e) {
            System.err.println("Gagal Simpan Laporan: " + e);
            return false;
        }
    }

    public List<LaporanModel> tampilLaporan() throws SQLException {
        List<LaporanModel> list = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection conn = (Connection) Connector.configDB();
        String sql = "SELECT * FROM laporan";
        
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while (rs.next()) {
                LaporanModel laporan = new LaporanModel();
                laporan.setId_laporan(rs.getInt("id_laporan"));
                laporan.setId_user(rs.getInt("id_user"));
                laporan.setJudul(rs.getString("judul"));
                laporan.setLokasi(rs.getString("lokasi"));
                laporan.setDeskripsi(rs.getString("deskripsi"));
                laporan.setJenis_kerusakan(rs.getString("jenis_kerusakan"));
                laporan.setStatus(rs.getString("status"));
                laporan.setCreated_at(rs.getTimestamp("created_at"));
                list.add(laporan);
            }
        } catch (HeadlessException | SQLException e) {
            System.err.println(e);
        }
        return list;
    }

    public boolean updateStatus(int id_laporan, String status) throws SQLException {
        PreparedStatement pstm = null;
        Connection conn = (Connection) Connector.configDB();
        String sql = "UPDATE laporan SET status = ? WHERE id_laporan = ?";
        
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, status);
            pstm.setInt(2, id_laporan);
            pstm.executeUpdate();
            return true;
        } catch (HeadlessException | SQLException e) {
            System.err.println("Gagal Update Status: " + e);
            return false;
        }
    }

    public boolean hapusData(int id_laporan) throws SQLException {
        PreparedStatement pstm = null;
        Connection conn = (Connection) Connector.configDB();
        String sql = "DELETE FROM laporan WHERE id_laporan = ?";
        
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id_laporan);
            pstm.executeUpdate();
            return true;
        } catch (HeadlessException | SQLException e) {
            System.err.println("Gagal Hapus: " + e);
            return false;
        }
    }
}
