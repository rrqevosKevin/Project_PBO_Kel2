package Controller;

import Model.UserModel;
import Model.PenugasanModel;
import View.Users;
import View.Admin;
import View.Penugasan;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AdmUserController extends BasicController {
    private UserModel model;
    private Users frm;
    private int idTerpilih = 0;

    public AdmUserController(UserModel model, Users frm) {
        this.model = model;
        this.frm = frm;

        this.frm.btnHapus.addActionListener(this);
        this.frm.tabeldata.addMouseListener(this);
        this.frm.nav1.addActionListener(this);
        this.frm.nav2.addActionListener(this);
        this.frm.btnLogout.addActionListener(this);

        TampilDataUser();
    }

    public void TampilDataUser() {
        DefaultTableModel tblModel = new DefaultTableModel();
        tblModel.addColumn("ID");
        tblModel.addColumn("Username");
        tblModel.addColumn("No HP");
        tblModel.addColumn("Role");

        try {
            List<UserModel> list = model.tampilUser();
            for (UserModel user : list) {
                tblModel.addRow(new Object[]{
                    user.getId_user(),
                    user.getUsername(),
                    user.getNo_hp(),
                    user.getRole()
                });
            }
            frm.tabeldata.setModel(tblModel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Tampil: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == frm.btnHapus) {
            if (idTerpilih == 0) {
                JOptionPane.showMessageDialog(null, "Pilih data di tabel terlebih dahulu!");
                return;
            }
            int konfirmasi = JOptionPane.showConfirmDialog(null,
                "Yakin ingin menghapus user ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                try {
                    if (model.hapusData(idTerpilih)) {
                        JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
                        TampilDataUser();
                        idTerpilih = 0;
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Gagal Hapus: " + e.getMessage());
                }
            }

        } else if (ae.getSource() == frm.btnLogout) {
            Logout(frm);

        } else if (ae.getSource() == frm.nav1) {
            Admin adminView = new Admin();
            new AdmLaporanController(new PenugasanModel(), adminView);
            PindahHalaman(adminView, frm);

        } else if (ae.getSource() == frm.nav2) {
            Penugasan penugasanView = new Penugasan();
            new AdmPenugasanController(new PenugasanModel(), penugasanView);
            PindahHalaman(penugasanView, frm);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == frm.tabeldata) {
            int baris = frm.tabeldata.rowAtPoint(me.getPoint());
            idTerpilih = Integer.parseInt(
                frm.tabeldata.getValueAt(baris, 0).toString()
            );
        }
    }
}