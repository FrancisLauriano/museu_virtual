package view;

import controller.UsuarioController;
import model.Usuario;
import utils.PasswordUtil;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private final UsuarioController usuarioController;
    private JTextField emailField;
    private JPasswordField senhaField;

    public LoginView() {
        this.usuarioController = new UsuarioController();
        setTitle("Login - Museu Virtual");
        setSize(1400, 800);    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel logoPanel = new JPanel(new GridBagLayout());
        logoPanel.setBackground(new Color(173, 216, 230));  
        logoPanel.setPreferredSize(new Dimension(500, 400));  

        GridBagConstraints logoGbc = new GridBagConstraints();
        logoGbc.gridx = 0;
        logoGbc.gridy = 0;
        logoGbc.insets = new Insets(0, 100, 0, 0);  
        logoGbc.anchor = GridBagConstraints.CENTER;

        JLabel logoLabel = new JLabel(new ImageIcon("src/main/resources/logo.png"));
        logoPanel.add(logoLabel, logoGbc);  

        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(new Color(173, 216, 230)); 
        JPanel modalPanel = new JPanel(new GridBagLayout());
        modalPanel.setBackground(Color.WHITE);
        modalPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        modalPanel.setPreferredSize(new Dimension(380, 220));

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setOpaque(false);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Margem interna

        Dimension inputSize = new Dimension(350, 40);

        emailField = new JTextField();
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));
        emailField.setPreferredSize(inputSize);

        senhaField = new JPasswordField();
        senhaField.setBorder(BorderFactory.createTitledBorder("Senha"));
        senhaField.setPreferredSize(inputSize);

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(150, 40));
        loginButton.addActionListener(e -> realizarLogin());

        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.gridx = 0;
        formGbc.gridy = GridBagConstraints.RELATIVE;
        formGbc.insets = new Insets(10, 0, 10, 0);  
        formGbc.anchor = GridBagConstraints.CENTER;

        loginPanel.add(emailField, formGbc);
        loginPanel.add(senhaField, formGbc);
        loginPanel.add(loginButton, formGbc);

        modalPanel.add(loginPanel);

        outerPanel.add(modalPanel);

        add(logoPanel, BorderLayout.WEST);  
        add(outerPanel, BorderLayout.CENTER);  
    }

    private void realizarLogin() {
        String email = emailField.getText().trim();
        String senha = new String(senhaField.getPassword()).trim();

        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, preencha todos os campos!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Usuario usuario = usuarioController.buscarUsuarioPorEmail(email);

            if (usuario != null && PasswordUtil.checkPassword(senha, usuario.getSenha())) {
                if (usuario.getTipoUsuario().equalsIgnoreCase("administrador")) {
                    new AdminPainelView().setVisible(true);
                } else if (usuario.getTipoUsuario().equalsIgnoreCase("aluno")) {
                    new AlunoPainelView().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Tipo de usuário inválido!",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dispose();  
            } else {
                JOptionPane.showMessageDialog(this,
                        "Email ou senha incorretos!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao conectar: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
