package org.example.demo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.demo.artist.Artist;
import org.example.demo.artist.ArtistDAO;
import org.example.demo.artist.ArtistDaoJpa;

import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demo");
        EntityManager em = emf.createEntityManager();

        ArtistDaoJpa artistDAO = new ArtistDaoJpa();
        try {
            Field emField = artistDAO.getClass().getDeclaredField("em");
            emField.setAccessible(true);
            emField.set(artistDAO, em);

            Artist artist = new Artist();
            artist.setName("John_Lemon");
            artist.setAbout("about john lemon");
            artistDAO.create(artist);

            artistDAO.getAll().forEach(a -> {
                System.out.println("Artist: " + a.getName());
            });

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
