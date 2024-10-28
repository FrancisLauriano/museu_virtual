package dao;

import model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;

public class UsuarioDAO extends InstanceDAO<Usuario> {

    protected Class<Usuario> getEntityClass() {
        return Usuario.class;
    }

    public List<Usuario> findAllUsuarios() {
        try {
            return findAll("FROM Usuario", Usuario.class);
        } catch (Exception e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
            return null;
        }
    }
    
    public List<Usuario> listarUsuariosPorTipo(String tipoUsuario) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "FROM Usuario u WHERE lower(u.tipoUsuario) = :tipoUsuario";
            return em.createQuery(jpql, Usuario.class)
                     .setParameter("tipoUsuario", tipoUsuario.toLowerCase())
                     .getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao listar usuários por tipo: " + e.getMessage());
            return List.of();
        }
    }

    public Usuario findUsuarioById(Long id) {
        try {
            return findById(id, Usuario.class);
        } catch (Exception e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
            return null;
        }
    }
    
    public Usuario findByEmail(String email) {
        try (EntityManager em = getEntityManager()) {
            return em.createQuery("FROM Usuario u WHERE u.email = :email", Usuario.class)
                     .setParameter("email", email)
                     .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Usuário não encontrado com o email: " + email);
            return null;
        } catch (Exception e) {
            System.err.println("Erro ao buscar usuário por email: " + e.getMessage());
            return null;
        }
    }

    public void saveUsuario(Usuario usuario) {
        try {
            save(usuario);
            System.out.println("Usuário salvo com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao salvar usuário: " + e.getMessage());
        }

    }
    
    public void updateUsuario(Usuario usuario) {
        try {
            update(usuario);
            System.out.println("Usuário atualizado com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }
    
    public void deleteUsuario(Long id) {
        try {
            Usuario usuario = findUsuarioById(id);
            if (usuario != null) {
                delete(usuario);
                System.out.println("Usuário excluído com sucesso.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao excluir usuário: " + e.getMessage());
        }
    }
}
