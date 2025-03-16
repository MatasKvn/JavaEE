package org.example.demo.artist;

import org.example.demo.generic.DAO;

public interface ArtistDAO extends DAO<Artist> {
    Artist getByName(String name);
}
