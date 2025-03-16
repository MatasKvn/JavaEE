package org.example.demo.lib;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.demo.song.Song;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.demo.song.SongDAO;
import org.example.demo.song.SongDaoJpa;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;

@ApplicationScoped
public class MyBatisProducer {
    private SqlSessionFactory sqlSessionFactory;

    private SqlSession sqlSession;

    public MyBatisProducer() {
        try (Reader reader = Resources.getResourceAsReader("mybatis-config.xml")) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException("Error loading MyBatis configuration", e);
        }
    }

    @Produces
    public SqlSessionFactory produceSqlSessionFactory() {
        return sqlSessionFactory;
    }

    @Produces
    public SqlSession produceSqlSession() {
        if (sqlSession == null) {
            sqlSession = sqlSessionFactory.openSession();
        }
        return sqlSession;
    }

    public static void main(String[] args) {
//        try (Reader reader = Resources.getResourceAsReader("mybatis-config.xml")) {
//            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
//            SqlSession session = sqlSessionFactory.openSession();
//
//            session.getMapper(UserMapper.class).getAll().forEach(user -> {
//                System.out.println("User: " + user.getName() + " (" + user.getEmail() + ")");
//            });
////            session.selectList("User.getAll").forEach(user -> {
////                System.out.println("User: " + user);
////            });
//        } catch (IOException e) {
//            throw new RuntimeException("Error loading MyBatis configuration", e);
//        }

        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("demo");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
//
//        EntityTransaction et = null;
//        et = entityManager.getTransaction();
//        et.begin();
////        Artist artist = new Artist();
////        artist.setName("John");
////        artist.setAbout("asdf");
////        entityManager.persist(artist);
////
////        Song gangnamStyle = new Song();
////        gangnamStyle.setDurationSeconds(180);
////        gangnamStyle.setReleaseDate(LocalDateTime.now());
////        gangnamStyle.setName("Gangnam Style");
////        gangnamStyle.setArtist(artist);
//
////        entityManager.persist(gangnamStyle);
//        Artist artist = entityManager.find(Artist.class, 1L);
//        Song song = entityManager.find(Song.class, 1L);
//        artist.getSongs().add(song);
//
//        et.commit();


        try {
            Song gentleman = entityManager.createQuery("SELECT u FROM Song u WHERE u.name = 'Gangnam Style'", Song.class)
                    .getResultList().get(0);

            SongDaoJpa songDAO = new SongDaoJpa();
            java.lang.reflect.Field em = SongDaoJpa.class.getDeclaredField("em");
            em.setAccessible(true);
            em.set(songDAO,  entityManager);

//            gentleman.getArtist().getSongs().remove(gentleman);
            songDAO.delete(gentleman.getId());
            entityManager.createQuery("SELECT u FROM Song u", Song.class)
                    .getResultList().forEach(song -> {
                        System.out.println(song.getName() + " " + song.getArtist().getName());
                    });
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

    }
}
