package org.example.demo.playlist;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@RequestScoped
public class PlaylistDaoJpa implements PlaylistDAO {

    @Inject
    private EntityManager em;

    @Override
    public List<Playlist> getAll() {
        return em.createQuery("SELECT p FROM Playlist p", Playlist.class).getResultList();
    }

    @Override
    public Playlist getById(long id) {
        return em.find(Playlist.class, id);
    }

    @Override
    public Playlist create(Playlist playlist) {
        em.getTransaction().begin();
        em.persist(playlist);
        em.getTransaction().commit();
        return playlist;
    }

    @Override
    public Playlist update(Playlist playlist) {
        em.getTransaction().begin();
        em.merge(playlist);
        em.getTransaction().commit();
        return playlist;
    }

    @Override
    public Playlist delete(long id) {
        em.getTransaction().begin();
        Playlist playlist = em.find(Playlist.class, id);
        if (playlist != null) {
            em.remove(playlist);
        }
        em.getTransaction().commit();
        return playlist;
    }
}
