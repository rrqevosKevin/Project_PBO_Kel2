package project_pbo_kel2;

import Controller.LoginController;
import Model.UserModel;
import View.Login;

public class Project_PBO_KEL2 {

    public static void main(String[] args) {
        // 1. Instansiasi Model
        UserModel modelUser = new UserModel();
        
        // 2. Instansiasi View (Form Login)
        Login formLogin = new Login();
        
        // 3. Hubungkan Model dan View menggunakan Controller
        new LoginController(modelUser, formLogin);
        
        // 4. Tampilkan Form ke Layar
        formLogin.setLocationRelativeTo(null); // Membuat form muncul pas di tengah layar
        formLogin.setVisible(true);
    }
    
}