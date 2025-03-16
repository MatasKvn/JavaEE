package org.example.demo.song;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

@RequestScoped
public class SongDaoJpa implements SongDAO {

    @Inject
    private EntityManager em;

    public SongDaoJpa() {}

    public List<Song> getAll() {
        return em.createQuery("SELECT s FROM Song s", Song.class).getResultList();
    }

    public Song getById(long id) {
        return em.find(Song.class, id);
    }

    public Song create(Song song) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(song);
        em.flush();
        tx.commit();
        return song;
    }

    public Song update(Song song) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.merge(song);

        tx.commit();
        return song;
    }

//    @Transactional
//    public Song delete(Song song) {
//        em.merge(song);
//        return song;
//    }

    public Song delete(long id) {
        em.getTransaction().begin();
        Song song = em.find(Song.class, id);
        if (song != null) {
            song.getArtist().getSongs().remove(song);
            em.remove(song);
        }
        em.getTransaction().commit();
        return song;
    }
}
