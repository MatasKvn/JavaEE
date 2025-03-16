package org.example.demo.song;

import jakarta.transaction.Transactional;
import org.example.demo.artist.Artist;
import org.example.demo.generic.DAO;

import java.util.List;

public interface SongDAO extends DAO<Song> {
}
