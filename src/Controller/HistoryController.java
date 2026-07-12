package Controller;

import Model.LaporanModel;
import Model.PenugasanModel;
import Model.UserModel;
import View.History;
import View.Admin;
import View.Penugasan;
import View.Users;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class HistoryController extends BasicController {
    private LaporanModel model;
    private History frm;

    public HistoryController(LaporanModel model, History frm) {
        this.model = model;
        this.frm = frm;

        this.frm.nav1.addActionListener(this);
        this.frm.nav2.addActionListener(this);
        this.frm.nav3.addActionListener(this);
        this.frm.btnLogout.addActionListener(this);

        TampilHistory();
    }

    public void TampilHistory() {
        DefaultTableModel tblModel = new DefaultTableModel();
        tblModel.addColumn("ID");
        tblModel.addColumn("Judul");
        tblModel.addColumn("Lokasi");
        tblModel.addColumn("Kerusakan");
        tblModel.addColumn("Status");
        tblModel.addColumn("Tanggal Laporan");

        try {
            List<LaporanModel> list = model.tampilLaporan();
            for (LaporanModel laporan : list) {
                tblModel.addRow(new Object[]{
                    laporan.getId_laporan(),
                    laporan.getJudul(),
                    laporan.getLokasi(),
                    laporan.getJenis_kerusakan(),
                    laporan.getStatus(),
                    laporan.getCreated_at()
                });
            }
            frm.tabeldata.setModel(tblModel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Tampil History: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == frm.btnLogout) {
            Logout(frm);

        } else if (ae.getSource() == frm.nav1) {
            Admin adminView = new Admin();
            new AdmLaporanController(new PenugasanModel(), adminView);
            PindahHalaman(adminView, frm);

        } else if (ae.getSource() == frm.nav2) {
            Penugasan penugasanView = new Penugasan();
            new AdmPenugasanController(new PenugasanModel(), penugasanView);
            PindahHalaman(penugasanView, frm);

        } else if (ae.getSource() == frm.nav3) {
            Users userView = new Users();
            new AdmUserController(new UserModel(), userView);
            PindahHalaman(userView, frm);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {}
}