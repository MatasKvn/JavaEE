package org.example.demo.artist;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import org.example.demo.song.Song;

import java.util.List;

@RequestScoped
public class ArtistDaoJpa implements ArtistDAO {

    @Inject
    private EntityManager em;

    @Override
    public List<Artist> getAll() {
        return em.createQuery("SELECT a FROM Artist a", Artist.class).getResultList();
    }

    @Override
    public Artist getById(long id) {
        return em.find(Artist.class, id);
    }

    @Override
    public Artist create(Artist artist) {
        em.getTransaction().begin();
        em.persist(artist);
        em.getTransaction().commit();
        return artist;
    }

    @Override
    public Artist update(Artist artist) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Artist updatedArtist = em.merge(artist);
        tx.commit();
        return updatedArtist;
    }

    @Override
    public Artist delete(long id) {
         em.getTransaction().begin();
         Artist artist = em.find(Artist.class, id);
         if (artist != null) {
             artist.getSongs().forEach(song -> em.remove(song));
             em.remove(artist);
         }
         em.getTransaction().commit();
         return artist;
    }

    @Override
    public Artist getByName(String name) {
        try {
            return em.createQuery("SELECT a FROM Artist a WHERE a.name = :name", Artist.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
