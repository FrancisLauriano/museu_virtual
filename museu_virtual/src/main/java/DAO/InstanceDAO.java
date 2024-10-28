package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public abstract class InstanceDAO<T> {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("museuPU");

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void save(T entity) {
        try (EntityManager em = getEntityManager()) {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        }
    }

    public T findById(Long id, Class<T> entityClass) {
        T entity;
        try (EntityManager em = getEntityManager()) {
            entity = em.find(entityClass, id);
        }
        return entity;
    }

    public List<T> findAll(String jpql, Class<T> entityClass) {
        List<T> result;
        try (EntityManager em = getEntityManager()) {
            result = em.createQuery(jpql, entityClass).getResultList();
        }
        return result;
    }

    public List<T> findWithParameter(String jpql, Class<T> entityClass, String paramName, Object paramValue) {
        try (EntityManager em = getEntityManager()) {
            return em.createQuery(jpql, entityClass)
                    .setParameter(paramName, paramValue)
                    .getResultList();
        }
    }

    public void update(T entity) {
        try (EntityManager em = getEntityManager()) {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        }
    }

    public void delete(T entity) {
        try (EntityManager em = getEntityManager()) {
            em.getTransaction().begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            em.getTransaction().commit();
        }
    }
    
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

}
