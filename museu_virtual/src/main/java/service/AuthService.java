package service;

import dao.UsuarioDAO;
import model.Usuario;
import utils.PasswordUtil;

public class AuthService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Autentica um usuário pelo email e senha.
     * @param email O email do usuário.
     * @param senha A senha em texto plano fornecida.
     * @return true se a autenticação for bem-sucedida, false caso contrário.
     */
    public boolean autenticar(String email, String senha) {
        // Normaliza o email para evitar inconsistências
        email = email.trim().toLowerCase();

        Usuario usuario = usuarioDAO.findByEmail(email);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return false;
        }

        System.out.println("Senha fornecida: " + senha);
        System.out.println("Hash armazenado: " + usuario.getSenha());

        boolean senhaCorreta = PasswordUtil.checkPassword(senha, usuario.getSenha());

        if (senhaCorreta) {
            System.out.println("Autenticação bem-sucedida.");
            return true;
        } else {
            System.out.println("Senha incorreta.");
            return false;
        }
    }

    /**
     * Registra um novo usuário no sistema.
     * @param nome O nome do usuário.
     * @param email O email do usuário.
     * @param senha A senha em texto plano.
     * @param tipoUsuario O tipo de usuário.
     */
    public void registrarUsuario(String nome, String email, String senha, String tipoUsuario) {
        email = email.trim().toLowerCase();

        if (usuarioDAO.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }

        System.out.println("Senha fornecida: " + senha);
        String senhaHash = PasswordUtil.hashPassword(senha);
        System.out.println("Hash gerado: " + senhaHash);

        Usuario novoUsuario = new Usuario(nome, email, senhaHash, tipoUsuario);
        usuarioDAO.save(novoUsuario);

        System.out.println("Usuário registrado com sucesso.");
    }
}
