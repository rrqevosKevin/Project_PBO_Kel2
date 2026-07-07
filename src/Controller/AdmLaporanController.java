package Controller;

import Model.PenugasanModel;
import Model.LaporanModel;
import Model.UserModel;
import View.Admin;
import View.Users;
import View.Penugasan;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AdmLaporanController extends BasicController {
    private PenugasanModel penugasanModel;
    private LaporanModel laporanModel;
    private Admin frm;
    private int idLaporanTerpilih = 0;

    public AdmLaporanController(PenugasanModel penugasanModel, Admin frm) {
        this.penugasanModel = penugasanModel;
        this.frm = frm;
        this.laporanModel = new LaporanModel();

        this.frm.btnKirim.addActionListener(this);
        this.frm.btnHapus.addActionListener(this);
        this.frm.tabeldata.addMouseListener(this);
        this.frm.nav1.addActionListener(this);
        this.frm.nav2.addActionListener(this);
        this.frm.btnLogout.addActionListener(this);

        TampilDataLaporan();
        IsiComboPetugas();
    }

    public void TampilDataLaporan() {
        DefaultTableModel tblModel = new DefaultTableModel();
        tblModel.addColumn("ID Laporan");
        tblModel.addColumn("ID Pelapor");
        tblModel.addColumn("Judul");
        tblModel.addColumn("Lokasi");
        tblModel.addColumn("Kerusakan");

        try {
            List<LaporanModel> list = laporanModel.tampilLaporan();
            for (LaporanModel laporan : list) {
                String status = laporan.getStatus();
                if (status == null || (!status.equalsIgnoreCase("Ditugaskan")
                        && !status.equalsIgnoreCase("Selesai"))) {
                    tblModel.addRow(new Object[]{
                        laporan.getId_laporan(),
                        laporan.getId_user(),
                        laporan.getJudul(),
                        laporan.getLokasi(),
                        laporan.getJenis_kerusakan()
                    });
                }
            }
            frm.tabeldata.setModel(tblModel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Tampil Laporan: " + e.getMessage());
        }
    }

    public void IsiComboPetugas() {
        try {
            UserModel userModel = new UserModel();
            List<UserModel> listPetugas = userModel.tampilPetugas();
            frm.combo.removeAllItems();
            for (UserModel petugas : listPetugas) {
                frm.combo.addItem(petugas.getId_user() + " - " + petugas.getUsername());
            }
        } catch (SQLException e) {
            System.err.println("Gagal isi combo: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == frm.btnKirim) {
            if (idLaporanTerpilih == 0) {
                JOptionPane.showMessageDialog(null, "Pilih laporan di tabel terlebih dahulu!");
                return;
            }
            if (frm.combo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Tidak ada petugas yang dipilih!");
                return;
            }

            String selectedPetugas = frm.combo.getSelectedItem().toString();
            int idPetugas = Integer.parseInt(selectedPetugas.split(" - ")[0]);

            PenugasanModel dataTugas = new PenugasanModel();
            dataTugas.setId_laporan(idLaporanTerpilih);
            dataTugas.setId_petugas(idPetugas);
            dataTugas.setTanggal_tugas(new Date(System.currentTimeMillis()));
            dataTugas.setCatatan_tugas("Menunggu tindakan petugas");

            try {
                if (penugasanModel.simpanPenugasan(dataTugas)) {
                    laporanModel.updateStatus(idLaporanTerpilih, "Ditugaskan");
                    JOptionPane.showMessageDialog(null, "Tugas berhasil dikirim ke petugas!");
                    TampilDataLaporan();
                    idLaporanTerpilih = 0;
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Gagal Kirim Tugas: " + e.getMessage());
            }

        } else if (ae.getSource() == frm.btnHapus) {
            if (idLaporanTerpilih == 0) {
                JOptionPane.showMessageDialog(null, "Pilih laporan di tabel terlebih dahulu!");
                return;
            }
            int konfirmasi = JOptionPane.showConfirmDialog(null,
                "Yakin ingin menghapus laporan ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                try {
                    if (laporanModel.hapusData(idLaporanTerpilih)) {
                        JOptionPane.showMessageDialog(null, "Laporan berhasil dihapus!");
                        TampilDataLaporan();
                        idLaporanTerpilih = 0;
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Gagal Hapus: " + e.getMessage());
                }
            }

        } else if (ae.getSource() == frm.btnLogout) {
            Logout(frm);

        } else if (ae.getSource() == frm.nav1) {
            Penugasan dataPenugasanView = new Penugasan();
            new AdmPenugasanController(new PenugasanModel(), dataPenugasanView);
            PindahHalaman(dataPenugasanView, frm);

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
            idLaporanTerpilih = Integer.parseInt(
                frm.tabeldata.getValueAt(baris, 0).toString()
            );
        }
    }
}