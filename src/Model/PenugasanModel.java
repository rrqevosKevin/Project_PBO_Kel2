package Model;

import java.util.*;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.Date;

public class PenugasanModel implements PenghapusData {

    public int getId_penugasan() {
        return id_penugasan;
    }

    public void setId_penugasan(int id_penugasan) {
        this.id_penugasan = id_penugasan;
    }

    public int getId_laporan() {
        return id_laporan;
    }

    public void setId_laporan(int id_laporan) {
        this.id_laporan = id_laporan;
    }

    public int getId_petugas() {
        return id_petugas;
    }

    public void setId_petugas(int id_petugas) {
        this.id_petugas = id_petugas;
    }

    public Date getTanggal_tugas() {
        return tanggal_tugas;
    }

    public void setTanggal_tugas(Date tanggal_tugas) {
        this.tanggal_tugas = tanggal_tugas;
    }

    public String getCatatan_tugas() {
        return catatan_tugas;
    }

    public void setCatatan_tugas(String catatan_tugas) {
        this.catatan_tugas = catatan_tugas;
    }
    
    public String getStatusLaporan() {
    return statusLaporan;
}

    public void setStatusLaporan(String statusLaporan) {
    this.statusLaporan = statusLaporan;
}
    private int id_penugasan;
    private int id_laporan;
    private int id_petugas;
    private Date tanggal_tugas;
    private String catatan_tugas;
    private String statusLaporan;


    
    public boolean simpanPenugasan(PenugasanModel data) throws SQLException {
        PreparedStatement pstm = null;
        Connection conn = (Connection) Connector.configDB();
        String sql = "INSERT INTO penugasan (id_laporan, id_petugas, tanggal_tugas, catatan_tugas) VALUES (?, ?, ?, ?)";
        
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, data.getId_laporan());
            pstm.setInt(2, data.getId_petugas());
            pstm.setDate(3, data.getTanggal_tugas());
            pstm.setString(4, data.getCatatan_tugas());
            pstm.executeUpdate();
            return true;
        } catch (HeadlessException | SQLException e) {
            System.err.println("Gagal Simpan Penugasan: " + e);
            return false;
        }
    }

    public List<PenugasanModel> tampilPenugasan() throws SQLException {
        List<PenugasanModel> list = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection conn = (Connection) Connector.configDB();
        String sql = "SELECT * FROM penugasan";
        
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while (rs.next()) {
                PenugasanModel penugasan = new PenugasanModel();
                penugasan.setId_penugasan(rs.getInt("id_penugasan"));
                penugasan.setId_laporan(rs.getInt("id_laporan"));
                penugasan.setId_petugas(rs.getInt("id_petugas"));
                penugasan.setTanggal_tugas(rs.getDate("tanggal_tugas"));
                penugasan.setCatatan_tugas(rs.getString("catatan_tugas"));
                list.add(penugasan);
            }
        } catch (HeadlessException | SQLException e) {
            System.err.println(e);
        }
        return list;
    }

    public boolean hapusData(int id_penugasan) throws SQLException {
        PreparedStatement pstm = null;
        Connection conn = (Connection) Connector.configDB();
        String sql = "DELETE FROM penugasan WHERE id_penugasan = ?";
        
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id_penugasan);
            pstm.executeUpdate();
            return true;
        } catch (HeadlessException | SQLException e) {
            System.err.println("Gagal Hapus Penugasan: " + e);
            return false;
        }
    }
    
        public List<PenugasanModel> tampilPenugasanDenganStatus() throws SQLException {
        List<PenugasanModel> list = new ArrayList<>();
        Connection conn = (Connection) Connector.configDB();
        String sql = "SELECT p.*, l.status FROM penugasan p " +
                     "JOIN laporan l ON p.id_laporan = l.id_laporan";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PenugasanModel tugas = new PenugasanModel();
                tugas.setId_penugasan(rs.getInt("id_penugasan"));
                tugas.setId_laporan(rs.getInt("id_laporan"));
                tugas.setId_petugas(rs.getInt("id_petugas"));
                tugas.setTanggal_tugas(rs.getDate("tanggal_tugas"));
                tugas.setCatatan_tugas(rs.getString("catatan_tugas"));
                tugas.setStatusLaporan(rs.getString("status"));
                list.add(tugas);
            }
        } catch (SQLException e) {
            System.err.println("Gagal Tampil Penugasan: " + e);
        }
        return list;
    }
    
}
