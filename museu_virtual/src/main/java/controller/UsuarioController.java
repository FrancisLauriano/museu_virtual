package controller;

import dao.UsuarioDAO;
import model.Usuario;
import java.util.regex.Pattern;
import utils.PasswordUtil; // usa BCrypt para implementar autenticação segura


import java.util.List;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public void adicionarUsuario(String nome, String email, String senha, String tipoUsuario) {
        try {
            nome = validarEntradaObrigatoria(nome, "Nome");
            tipoUsuario = validarTipoUsuario(tipoUsuario);
            senha = validarSenhaObrigatoria(senha, "Senha");    
            email = email.trim().toLowerCase(); 

            if (!isEmail(email)) {
                throw new IllegalArgumentException("Email inválido.");
            }
            
            String senhaHash = PasswordUtil.hashPassword(senha);

            Usuario usuario = new Usuario(nome, email, senhaHash, tipoUsuario);
            usuarioDAO.saveUsuario(usuario);
            System.out.println("Usuário cadastrado com sucesso.");

        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }
    
    public List<Usuario> listarTodosUsuarios() {
        try {
            return usuarioDAO.findAllUsuarios();
        } catch (Exception e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
            return null;
        }
    }
    
    public List<Usuario> listarUsuariosPorTipo(String tipoUsuario) {
        try {
            // Valida o tipo de usuário
            tipoUsuario = validarTipoUsuario(tipoUsuario);

            // Chama o método do DAO para buscar os usuários por tipo
            return usuarioDAO.listarUsuariosPorTipo(tipoUsuario);

        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao listar usuários por tipo: " + e.getMessage());
            return null;
        }
    }

    public Usuario buscarUsuarioPorId(Long id) {
        try {
            return usuarioDAO.findUsuarioById(id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
            return null;
        }
    }

    public void atualizarUsuario(Long id, String novoNome, String novoEmail, String novaSenha, String novoTipoUsuario) {
        try {
            Usuario usuario = usuarioDAO.findUsuarioById(id);
            if (usuario == null) {
                System.err.println("Usuário não encontrado.");
                return;
            }
            
            if (!isEmail(novoEmail)) {
                throw new IllegalArgumentException("Email inválido.");
            }
            
            if (novaSenha != null && !novaSenha.trim().isEmpty()) {
                novaSenha = validarSenhaObrigatoria(novaSenha, "Nova Senha");
                //novaSenha = novaSenha.trim();
                String senhaHash = PasswordUtil.hashPassword(novaSenha);
                usuario.setSenha(senhaHash);
            }
 
            novoNome = validarEntradaObrigatoria(novoNome, "Nome");
            novoEmail = novoEmail.trim().toLowerCase();
            novoTipoUsuario = validarTipoUsuario(novoTipoUsuario);

            usuario.setNome(novoNome);
            usuario.setEmail(novoEmail);
            usuario.setTipoUsuario(novoTipoUsuario);
            
            usuarioDAO.updateUsuario(usuario);
            System.out.println("Usuário atualizado com sucesso.");
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    public void deletarUsuario(Long id) {
        try {
            usuarioDAO.deleteUsuario(id);
            System.out.println("Usuário excluído com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao excluir usuário: " + e.getMessage());
        }
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        try {
            Usuario usuario = usuarioDAO.findByEmail(email.trim().toLowerCase());
            if (usuario == null) {
                System.out.println("Usuário não encontrado.");
            }
            return usuario;
        } catch (Exception e) {
            System.err.println("Erro ao buscar usuário por email: " + e.getMessage());
            return null;
        }
    }
    
    private String validarEntradaObrigatoria(String entrada, String campo) {
        if (entrada == null || entrada.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo '" + campo + "' é obrigatório e não pode ser vazio.");
        }	
        return entrada.trim().toLowerCase();
    }

    private boolean isEmail(String email) {
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
   
        return !(email == null || !emailPattern.matcher(email).matches());
    }
    
    private String validarSenhaObrigatoria(String senha, String campo) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo '" + campo + "' é obrigatório e não pode ser vazio.");
        }

        senha = senha.trim();  

        if (senha.contains(" ")) {
            throw new IllegalArgumentException("A senha não pode conter espaços em branco.");
        }

        if (senha.length() < 6 || senha.length() > 10) {
            throw new IllegalArgumentException(
               "O campo '" + campo + "' deve ter entre 6 e 10 caracteres."
            );
        }
        return senha;
    }

    
    private String validarTipoUsuario(String tipoUsuario) {
        if (tipoUsuario == null || tipoUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo de usuário é obrigatório.");
        }

        tipoUsuario = tipoUsuario.trim().toLowerCase();

        if (!tipoUsuario.equals("administrador") && !tipoUsuario.equals("aluno")) {
            throw new IllegalArgumentException("Tipo de usuário inválido. Deve ser 'administrador' ou 'aluno'.");
        }

        return tipoUsuario;
    }
}
