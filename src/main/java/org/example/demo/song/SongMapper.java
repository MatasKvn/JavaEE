package org.example.demo.song;

import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SongMapper {
    @Select("SELECT * FROM song") // Ensure the table name is correct
    List<Song> getAll();

    @Select("SELECT * FROM song WHERE id = #{id}") // Ensure the table name is correct
    Song getById(@Param("id") long id);

    @Insert("INSERT INTO song (name, release_date, duration_seconds) VALUES (#{name}, #{releaseDate}, #{durationSeconds})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Song song);

    @Update("UPDATE song SET name = #{name}, release_date = #{releaseDate}, duration_seconds = #{durationSeconds} WHERE id = #{id}")
    void update(Song song);

    @Delete("DELETE FROM song WHERE id = #{id}")
    void delete(@Param("id") long id);
}