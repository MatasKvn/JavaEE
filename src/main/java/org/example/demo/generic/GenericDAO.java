package org.example.demo.generic;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public abstract class GenericDAO<T> implements DAO<T> {

    protected Class<T> type;

    public GenericDAO(Class<T> type) {
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
    public T create(T T) {
        em.getTransaction().begin();
        em.persist(T);
        em.getTransaction().commit();
        return T;
    }

    @Override
    public T update(T T) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        T updatedT = em.merge(T);
        tx.commit();
        return updatedT;
    }
}
