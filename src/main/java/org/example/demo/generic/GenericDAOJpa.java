package org.example.demo.generic;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public abstract class GenericDAOJpa<T> implements DAO<T> {

    protected Class<T> type;

    public GenericDAOJpa(Class<T> type) {
        this.type = type;
    }

    @Inject
    private EntityManager em;

    @Override
    public List<T> getAll() {
        return em.createQuery("SELECT a FROM " + type.getName() + " a", type).getResultList();
    }

    @Override
    public T getById(long id) {
        return em.find(type, id);
    }

    @Override
    public T create(T t) {
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
        return t;
    }

    @Override
    public T update(T t) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        T updatedT = em.merge(t);
        tx.commit();
        return updatedT;
    }

    @Override
    public T delete(long id) {
        em.getTransaction().begin();
        T t = em.find(type, id);
        if (t != null) {
            em.remove(t);
        }
        em.getTransaction().commit();
        return t;
    }
}
