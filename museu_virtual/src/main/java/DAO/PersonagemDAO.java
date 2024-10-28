package dao;

import model.Personagem;
import java.util.List;
import jakarta.persistence.EntityManager;

public class PersonagemDAO extends InstanceDAO<Personagem> {

    protected Class<Personagem> getEntityClass() {
        return Personagem.class;
    }

    public List<Personagem> findAllPersonagens() {
        try {
            return findAll("FROM Personagem", Personagem.class);
        } catch (Exception e) {
            System.err.println("Erro ao listar personagens: " + e.getMessage());
            return List.of();
        }
    }
    
   public List<Personagem> listarPersonagensPorTipo(String tipo) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "FROM Personagem p WHERE lower(p.tipo) = :tipo";
            return em.createQuery(jpql, Personagem.class)
                     .setParameter("tipo", tipo.toLowerCase())
                     .getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao listar personagens por tipo: " + e.getMessage());
            return List.of();
        }
    }
 
    public Personagem findPersonagemById(Long id) {
        try {
            return findById(id, Personagem.class);
        } catch (Exception e) {
            System.err.println("Erro ao buscar personagem: " + e.getMessage());
            return null;
        }
    }

    public List<Personagem> buscarPersonagemPorNome(String nome) {
        try {
            String jpql = "FROM Personagem p WHERE p.nome = :nome";
            return findWithParameter(jpql, Personagem.class, "nome", nome);
        } catch (Exception e) {
            System.err.println("Erro ao buscar personagem por nome: " + e.getMessage());
            return null;
        }   
    }
    
    public List<Personagem> buscarPersonagemPorNomeETipo(String nome, String tipo) {
        try {
            String jpql = "FROM Personagem p WHERE lower(p.nome) = :nome AND lower(p.tipo) = :tipo";
            return getEntityManager().createQuery(jpql, Personagem.class)
                    .setParameter("nome", nome.toLowerCase())
                    .setParameter("tipo", tipo.toLowerCase())
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao buscar personagem por nome e tipo: " + e.getMessage());
            return List.of();
        }
    }

    public void savePersonagem(Personagem personagem) {
        try {
            save(personagem);
            System.out.println("Personagem salvo com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao salvar personagem: " + e.getMessage());
        }
    }

    public void updatePersonagem(Personagem personagem) {
        try {
            update(personagem);
            System.out.println("Personagem atualizado com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar personagem: " + e.getMessage());
        }
    }

    public void deletePersonagem(Long id) {
        try {
            Personagem personagem = findPersonagemById(id);
            if (personagem != null) {
                delete(personagem);
                System.out.println("Personagem excluído com sucesso.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao excluir personagem: " + e.getMessage());
        }
    }
}
