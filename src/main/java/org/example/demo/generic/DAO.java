package org.example.demo.generic;

import org.example.demo.artist.Artist;

import java.util.List;

public interface DAO<T> {
    List<T> getAll();

    T getById(long id);

    T create(T T);

    T update(T T);

    T delete(long id);
}
