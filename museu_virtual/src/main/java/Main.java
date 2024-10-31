import view.LoginView;
import service.FirebaseService;
import javax.swing.SwingUtilities;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            FirebaseService.initializeFirebase();
            System.out.println("Firebase inicializado com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao inicializar o Firebase: " + e.getMessage());
            return;          }

        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });
    }   
}
