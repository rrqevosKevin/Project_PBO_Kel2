package project_pbo_kel2;

import Controller.LoginController;
import Model.UserModel;
import View.Login;

public class Project_PBO_KEL2 {

    public static void main(String[] args) {
        
        UserModel modelUser = new UserModel();
        Login formLogin = new Login();
        new LoginController(modelUser, formLogin);
        formLogin.setVisible(true);
    }
    
}