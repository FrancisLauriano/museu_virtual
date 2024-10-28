package utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.function.Consumer;

public class JPAUtils {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("museuPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.accept(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public static void shutdown() {
        if (emf != null) {
            emf.close();
        }
    }
}

