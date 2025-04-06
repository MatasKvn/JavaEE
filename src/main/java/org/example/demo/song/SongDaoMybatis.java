package org.example.demo.song;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.example.demo.interceptors.PleaseLogThis;

import java.util.List;

@Alternative
@RequestScoped
public class SongDaoMybatis implements SongDAO {

    @Inject
    private SqlSession sqlSession; // Inject MyBatis session

    @PleaseLogThis
    @Override
    public List<Song> getAll() {
        return sqlSession.getMapper(SongMapper.class).getAll();
    }

    @Override
    public Song getById(long id) {
        return sqlSession.getMapper(SongMapper.class).getById(id);
    }

    @Override
    public Song create(Song song) {
        sqlSession.getMapper(SongMapper.class).insert(song);
        sqlSession.commit();
        return song;
    }

    @Override
    public Song update(Song song) {
        sqlSession.getMapper(SongMapper.class).update(song);
        sqlSession.commit();
        return song;
    }

    @Override
    public Song delete(long id) {
        sqlSession.getMapper(SongMapper.class).deleteReferences(id);
        sqlSession.getMapper(SongMapper.class).delete(id);
        sqlSession.commit();
        return null;
    }
}
