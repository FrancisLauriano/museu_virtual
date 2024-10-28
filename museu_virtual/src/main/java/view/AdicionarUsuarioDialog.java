package view;

import controller.UsuarioController;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class AdicionarUsuarioDialog extends JDialog {

    private final UsuarioController usuarioController;
    private JTextField nomeField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private JComboBox<String> tipoUsuarioComboBox;

    public AdicionarUsuarioDialog(Frame owner, UsuarioController usuarioController) {
        super(owner, "Adicionar Usuário", true);
        this.usuarioController = usuarioController;
        setSize(400, 300);
        setLocationRelativeTo(owner);
        initUI();
    }

    private void initUI() {
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField(20);
        formPanel.add(nomeField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField(20);
        formPanel.add(emailField);

        formPanel.add(new JLabel("Senha:"));
        senhaField = new JPasswordField(20);
        formPanel.add(senhaField);

        formPanel.add(new JLabel("Tipo de Usuário:"));
        tipoUsuarioComboBox = new JComboBox<>(new String[]{"administrador", "aluno"});
        formPanel.add(tipoUsuarioComboBox);

        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> salvarUsuario());

        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(salvarButton);
        buttonPanel.add(cancelarButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void salvarUsuario() {
        String nome = nomeField.getText().trim();
        String email = emailField.getText().trim();
        String senha = new String(senhaField.getPassword()).trim();
        String tipoUsuario = (String) tipoUsuarioComboBox.getSelectedItem();

        try {
            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || tipoUsuario == null) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "E-mail inválido.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidPassword(senha)) {
                JOptionPane.showMessageDialog(this, "A senha deve ter entre 6 e 10 caracteres.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (usuarioController.buscarUsuarioPorEmail(email) != null) {
                JOptionPane.showMessageDialog(this, "O email já está em uso por outro usuário.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            usuarioController.adicionarUsuario(nome, email, senha, tipoUsuario);
            JOptionPane.showMessageDialog(this, "Usuário adicionado com sucesso.");
            dispose(); 

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar usuário: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isValidPassword(String senha) {
        return senha.length() >= 6 && senha.length() <= 10;
    }

    //public static void main(String[] args) {
        //SwingUtilities.invokeLater(() -> {
           // UsuarioController usuarioController = new UsuarioController();
            //new AdicionarUsuarioDialog(null, usuarioController).setVisible(true);
        //});
    //}
}
