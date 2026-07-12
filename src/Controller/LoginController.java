package Controller;

import Model.UserModel;
import Model.LaporanModel;
import Model.PenugasanModel;
import View.Login;
import View.Register;
import View.Admin;
import View.PetugasView;
import View.UserBiasa;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class LoginController implements ActionListener {
    private UserModel model;
    private Login frm;

    public LoginController(UserModel model, Login frm) {
        this.model = model;
        this.frm = frm;
        this.frm.btnMasuk.addActionListener(this);
        this.frm.btnDaftar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == frm.btnMasuk) {
            String username = frm.txtUsername.getText();
            String password = new String(frm.txtPassword.getPassword()); 
            
            try {
                UserModel userLogin = model.login(username, password);
                
                if (userLogin != null) {
                    JOptionPane.showMessageDialog(null, "Login Berhasil! Selamat datang, " + userLogin.getUsername());
                    
                    if (userLogin.getRole().equals("admin")) {
                        Admin adminView = new Admin();
                        PenugasanModel penugasanModel = new PenugasanModel();
                        new AdmLaporanController(penugasanModel, adminView); 
                        adminView.setLocationRelativeTo(null);
                        adminView.setVisible(true);
                        
                    } else if (userLogin.getRole().equals("petugas")) {
                        PetugasView petugasView = new PetugasView();
                        LaporanModel laporanModel = new LaporanModel();
                        new PetugasController(laporanModel, petugasView); 
                        
                        petugasView.setLocationRelativeTo(null);
                        petugasView.setVisible(true);
                        
                    } else {
                        UserBiasa userBiasaView = new UserBiasa();
                        LaporanModel laporanModel = new LaporanModel();
                        new LaporanController(laporanModel, userBiasaView, userLogin.getId_user()); // ← kirim id
                        userBiasaView.setLocationRelativeTo(null);
                        userBiasaView.setVisible(true);
                    }
                    
                    frm.dispose(); 
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Username atau Password salah!");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error Database: " + ex.getMessage());
            }
            
        } else if (ae.getSource() == frm.btnDaftar) {
            Register regView = new Register();
            new RegisterController(model, regView); 
            regView.setLocationRelativeTo(null);
            regView.setVisible(true);
            frm.dispose(); 
        }
    }
}