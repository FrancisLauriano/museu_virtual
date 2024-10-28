package main;

import view.LoginView;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });
    }
}


