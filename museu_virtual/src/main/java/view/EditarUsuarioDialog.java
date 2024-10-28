package view;

import controller.UsuarioController;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;
//import utils.PasswordUtil;

public class EditarUsuarioDialog extends JDialog {

    private final UsuarioController usuarioController;
    private final Usuario usuario;
    private JTextField nomeField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private JComboBox<String> tipoUsuarioBox;

    public EditarUsuarioDialog(JFrame parent, UsuarioController usuarioController, Usuario usuario) {
        super(parent, "Editar Usuário", true);
        this.usuarioController = usuarioController;
        this.usuario = usuario;

        setSize(400, 300);
        setLocationRelativeTo(parent);
        initUI();
    }

    private void initUI() {
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Nome:"));
        nomeField = new JTextField(usuario.getNome(), 20);
        add(nomeField);

        add(new JLabel("Email:"));
        emailField = new JTextField(usuario.getEmail(), 20);
        add(emailField);

        add(new JLabel("Senha:"));
        senhaField = new JPasswordField(20); 
        add(senhaField);

        add(new JLabel("Tipo de Usuário:"));
        tipoUsuarioBox = new JComboBox<>(new String[]{"administrador", "aluno"});
        tipoUsuarioBox.setSelectedItem(usuario.getTipoUsuario());
        add(tipoUsuarioBox);

        JButton salvarButton = new JButton("Salvar");
        JButton cancelarButton = new JButton("Cancelar");

        salvarButton.addActionListener(e -> salvarUsuario());
        cancelarButton.addActionListener(e -> dispose());

        add(salvarButton);
        add(cancelarButton);
    }

    private void salvarUsuario() {
        String novoNome = nomeField.getText().trim();
        String novoEmail = emailField.getText().trim();
        String novaSenha = new String(senhaField.getPassword()).trim();
        String novoTipoUsuario = (String) tipoUsuarioBox.getSelectedItem();

        if (novoNome.isEmpty() || novoEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidEmail(novoEmail)) {
            JOptionPane.showMessageDialog(this, "E-mail inválido.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!usuario.getEmail().equalsIgnoreCase(novoEmail)) {
            Usuario usuarioExistente = usuarioController.buscarUsuarioPorEmail(novoEmail);
            if (usuarioExistente != null) {
                JOptionPane.showMessageDialog(this, "Email já está em uso por outro usuário.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        boolean senhaFoiAlterada = !novaSenha.isEmpty();

        if (senhaFoiAlterada && !isValidPassword(novaSenha)) {
            JOptionPane.showMessageDialog(this, "A nova senha deve ter entre 6 e 10 caracteres.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        usuario.setNome(novoNome);
        usuario.setEmail(novoEmail);
        
        if (senhaFoiAlterada) {
            usuario.setSenha(novaSenha);  
        }
        usuario.setTipoUsuario(novoTipoUsuario);

        usuarioController.atualizarUsuario(usuario.getId(), novoNome, novoEmail, senhaFoiAlterada ? novaSenha : null, novoTipoUsuario);
        JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso.");
        dispose();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isValidPassword(String senha) {
        return senha.length() >= 6 && senha.length() <= 10;
    }
}
