package Controller;

import Model.LaporanModel;
import View.PetugasView;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PetugasController extends BasicController {
    private LaporanModel laporanModel;
    private PetugasView frm;
    private int idLaporanTerpilih = 0;

    public PetugasController(LaporanModel laporanModel, PetugasView frm) {
        this.laporanModel = laporanModel;
        this.frm = frm;

        this.frm.btnSelesai.addActionListener(this);
        this.frm.btnLogout.addActionListener(this);
        this.frm.tabeldata.addMouseListener(this);

        TampilDataTugas();
    }

    public void TampilDataTugas() {
        DefaultTableModel tblModel = new DefaultTableModel();
        tblModel.addColumn("Judul");
        tblModel.addColumn("Lokasi");
        tblModel.addColumn("Deskripsi");
        tblModel.addColumn("Jenis Kerusakan");
        tblModel.addColumn("ID_Laporan");
        tblModel.addColumn("Status");

        try {
            List<LaporanModel> list = laporanModel.tampilLaporan();
            for (LaporanModel laporan : list) {
                if (laporan.getStatus().equalsIgnoreCase("Ditugaskan")) {
                    tblModel.addRow(new Object[]{
                        laporan.getJudul(),
                        laporan.getLokasi(),
                        laporan.getDeskripsi(),
                        laporan.getJenis_kerusakan(),
                        laporan.getId_laporan(),
                        laporan.getStatus()
                    });
                }
            }
            frm.tabeldata.setModel(tblModel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Tampil Tugas: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == frm.btnSelesai) {
            if (idLaporanTerpilih == 0) {
                JOptionPane.showMessageDialog(null, "Pilih tugas di tabel terlebih dahulu!");
                return;
            }
            int konfirmasi = JOptionPane.showConfirmDialog(null,
                "Tandai tugas ini sebagai selesai?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                try {
                    if (laporanModel.updateStatus(idLaporanTerpilih, "Selesai")) {
                        JOptionPane.showMessageDialog(null, "Tugas berhasil diselesaikan!");
                        TampilDataTugas();
                        idLaporanTerpilih = 0;
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Gagal Update Status: " + e.getMessage());
                }
            }

        } else if (ae.getSource() == frm.btnLogout) {
            Logout(frm);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == frm.tabeldata) {
            int baris = frm.tabeldata.rowAtPoint(me.getPoint());
            idLaporanTerpilih = Integer.parseInt(
                frm.tabeldata.getValueAt(baris, 4).toString()
            );
        }
    }
}