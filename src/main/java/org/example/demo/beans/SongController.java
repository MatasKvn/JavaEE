package org.example.demo.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.example.demo.artist.Artist;
import org.example.demo.artist.ArtistDAO;
import org.example.demo.song.Song;
import org.example.demo.song.SongDAO;

import java.time.LocalDateTime;
import java.util.List;

@Named
@RequestScoped
public class SongController {
    @Inject
    private SongDAO songDao;

    @Inject
    private ArtistDAO artistDao;

    @Getter @Setter
    private String name;
    @Getter @Setter
    private int durationSeconds;
    @Getter @Setter
    private String artistName;

    @Getter @Setter
    private String errorMessage = "";

    public List<Song> getSongs() {
        return songDao.getAll();
    }

    public String getFormattedSongTime(Song song) {
        return String.format("%d:%02d", song.getDurationSeconds() / 60, song.getDurationSeconds() % 60);
    }

    public void deleteSong(Song song) {
        songDao.delete(song.getId());
    }

    public void createSong() {
        Song song = new Song();
        song.setName(name);
        song.setDurationSeconds(durationSeconds);
        song.setReleaseDate(LocalDateTime.now());
        Artist artist = artistDao.getByName(artistName);
        if (artist == null) {
            setErrorMessage(String.format("Artist '%s' does not exist", artistName));
            return;
        }
        song.setArtist(artist);
        artist.getSongs().add(song);
        songDao.create(song);
        artistDao.update(artist);
    }
}
