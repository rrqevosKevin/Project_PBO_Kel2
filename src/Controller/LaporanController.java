package Controller;

import Model.LaporanModel;
import View.UserBiasa;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class LaporanController extends BasicController {
    private LaporanModel model;
    private UserBiasa frm;
    private int idUserAktif = 1;

    public LaporanController(LaporanModel model, UserBiasa frm) {
        this.model = model;
        this.frm = frm;

        this.frm.btnKirim.addActionListener(this);
        this.frm.btnLogout.addActionListener(this);
        this.frm.tabeldata.addMouseListener(this);

        TampilDataLaporan();
    }

    public void TampilDataLaporan() {
        DefaultTableModel tblModel = new DefaultTableModel();
        tblModel.addColumn("ID");
        tblModel.addColumn("Judul");
        tblModel.addColumn("Lokasi");
        tblModel.addColumn("Kerusakan");
        tblModel.addColumn("Status");

        try {
            List<LaporanModel> list = model.tampilLaporan();
            for (LaporanModel laporan : list) {
                tblModel.addRow(new Object[]{
                    laporan.getId_laporan(),
                    laporan.getJudul(),
                    laporan.getLokasi(),
                    laporan.getJenis_kerusakan(),
                    laporan.getStatus()
                });
            }
            frm.tabeldata.setModel(tblModel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Tampil: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == frm.btnKirim) {
            LaporanModel dataLaporan = new LaporanModel();
            dataLaporan.setId_user(idUserAktif);
            dataLaporan.setJudul(frm.txtJudul.getText());
            dataLaporan.setLokasi(frm.txtLokasi.getText());
            dataLaporan.setJenis_kerusakan(frm.combo.getSelectedItem().toString());

            try {
                dataLaporan.setDeskripsi(frm.txtDeskripsi.getText());
            } catch (Exception e) {
                dataLaporan.setDeskripsi("");
            }

            try {
                if (model.simpanLaporan(dataLaporan)) {
                    JOptionPane.showMessageDialog(null, "Laporan berhasil dikirim!");
                    TampilDataLaporan();
                    frm.txtJudul.setText("");
                    frm.txtLokasi.setText("");
                    frm.txtDeskripsi.setText("");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Gagal Kirim: " + e.getMessage());
            }

        } else if (ae.getSource() == frm.btnLogout) {
            Logout(frm);
        }
    }

        @Override
        public void mouseClicked(MouseEvent me) {}
}