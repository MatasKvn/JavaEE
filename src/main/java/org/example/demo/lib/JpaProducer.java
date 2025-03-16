package org.example.demo.lib;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class JpaProducer {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("demo");

    @Produces
    @RequestScoped
    public EntityManager createEm() {
        return emf.createEntityManager();
    }

    public void close(@Disposes EntityManager em) {
        if (em.isOpen()) {
            em.close();
        }
    }

    @PreDestroy
    public void destroyEmf() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
