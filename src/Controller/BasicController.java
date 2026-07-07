package Controller;

import Model.UserModel;
import View.Login;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public abstract class BasicController implements ActionListener, MouseListener {

    protected void Logout(JFrame frm) {
        int konfirmasi = JOptionPane.showConfirmDialog(null, 
            "Yakin ingin keluar?", "Logout", JOptionPane.YES_NO_OPTION);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            Login loginView = new Login();
            new LoginController(new UserModel(), loginView);
            loginView.setLocationRelativeTo(null);
            loginView.setVisible(true);
            frm.dispose();
        }
    }

    protected void PindahHalaman(JFrame viewBaru, JFrame frm) {
        viewBaru.setLocationRelativeTo(null);
        viewBaru.setVisible(true);
        frm.dispose();
    }

    @Override public void mousePressed(MouseEvent me) {}
    @Override public void mouseReleased(MouseEvent me) {}
    @Override public void mouseEntered(MouseEvent me) {}
    @Override public void mouseExited(MouseEvent me) {}
}

