package Controller;

import Model.UserModel;
import View.Register;
import View.Login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class RegisterController implements ActionListener {
    private UserModel model;
    private Register frm;

    public RegisterController(UserModel model, Register frm) {
        this.model = model;
        this.frm = frm;
        this.frm.btnDaftar.addActionListener(this);
        this.frm.btnKembali.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == frm.btnDaftar) {
            UserModel dataBaru = new UserModel();
            dataBaru.setUsername(frm.txtUsername.getText());
            dataBaru.setPassword(new String(frm.txtPassword.getPassword()));
            dataBaru.setNo_hp(frm.txtHP.getText());
            dataBaru.setRole("masyarakat"); 
            
            try {
                if (model.register(dataBaru)) {
                    JOptionPane.showMessageDialog(null, "Pendaftaran Berhasil! Silakan Login.");
                    Login loginView = new Login();
                    new LoginController(model, loginView);
                    loginView.setLocationRelativeTo(null);
                    loginView.setVisible(true);
                    frm.dispose();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Gagal Mendaftar: " + e.getMessage());
            }
            
        } else if (ae.getSource() == frm.btnKembali) {
            Login loginView = new Login();
            new LoginController(model, loginView);
            loginView.setLocationRelativeTo(null);
            loginView.setVisible(true);
            frm.dispose();
        }
    }
}