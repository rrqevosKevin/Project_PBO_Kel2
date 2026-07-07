package Controller;

import Model.PenugasanModel;
import Model.UserModel;
import Model.LaporanModel;
import View.Penugasan;
import View.Admin;
import View.Users;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AdmPenugasanController extends BasicController {
    private PenugasanModel model;
    private Penugasan frm;
    private int idPenugasanTerpilih = 0;
    private int idLaporanTerkait = 0;

    public AdmPenugasanController(PenugasanModel model, Penugasan frm) {
        this.model = model;
        this.frm = frm;

        this.frm.btnHapus.addActionListener(this);
        this.frm.tabeldata.addMouseListener(this);
        this.frm.nav1.addActionListener(this);
        this.frm.nav2.addActionListener(this);
        this.frm.btnLogout.addActionListener(this);

        TampilDataPenugasan();
    }

    public void TampilDataPenugasan() {
        DefaultTableModel tblModel = new DefaultTableModel();
        tblModel.addColumn("ID Penugasan");
        tblModel.addColumn("ID Laporan");
        tblModel.addColumn("ID Petugas");
        tblModel.addColumn("Tanggal");
        tblModel.addColumn("Catatan");
        tblModel.addColumn("Status");  

        try {
            List<PenugasanModel> list = model.tampilPenugasanDenganStatus();
            for (PenugasanModel tugas : list) {
                tblModel.addRow(new Object[]{
                    tugas.getId_penugasan(),
                    tugas.getId_laporan(),
                    tugas.getId_petugas(),
                    tugas.getTanggal_tugas(),
                    tugas.getCatatan_tugas(),
                    tugas.getStatusLaporan()
                });
            }
            frm.tabeldata.setModel(tblModel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Tampil Penugasan: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == frm.btnHapus) {
            if (idPenugasanTerpilih == 0) {
                JOptionPane.showMessageDialog(null, "Pilih data penugasan di tabel terlebih dahulu!");
                return;
            }
            int konfirmasi = JOptionPane.showConfirmDialog(null,
                "Yakin ingin menghapus penugasan ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                try {
                    if (model.hapusData(idPenugasanTerpilih)) {
                        LaporanModel lapModel = new LaporanModel();
                        lapModel.updateStatus(idLaporanTerkait, "Menunggu");
                        
                        JOptionPane.showMessageDialog(null, "Penugasan berhasil dihapus!");
                        TampilDataPenugasan();
                        idPenugasanTerpilih = 0;
                        idLaporanTerkait = 0;
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Gagal Hapus Penugasan: " + e.getMessage());
                }
            }

        } else if (ae.getSource() == frm.btnLogout) {
            Logout(frm);

        } else if (ae.getSource() == frm.nav1) {
            Admin adminView = new Admin();
            new AdmLaporanController(new PenugasanModel(), adminView);
            PindahHalaman(adminView, frm);

        } else if (ae.getSource() == frm.nav2) {
            Users userView = new Users();
            new AdmUserController(new UserModel(), userView);
            PindahHalaman(userView, frm);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == frm.tabeldata) {
            int baris = frm.tabeldata.rowAtPoint(me.getPoint());
            idPenugasanTerpilih = Integer.parseInt(frm.tabeldata.getValueAt(baris, 0).toString());
            idLaporanTerkait = Integer.parseInt(frm.tabeldata.getValueAt(baris, 1).toString());
        }
    }
}