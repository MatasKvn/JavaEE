package org.example.demo.beans;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.example.demo.playlist.Playlist;
import org.example.demo.playlist.PlaylistDAO;
import org.example.demo.song.Song;
import org.example.demo.song.SongDAO;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class PlaylistController implements Serializable {
    @Inject
    private PlaylistDAO playlistDAO;

    @Inject
    private SongDAO songDAO;

    @Setter
    private Playlist playlist = null;

    @Getter @Setter
    private String errMsg = null;

    public String requirePlaylistSelect() {
        if (playlist == null) {
            return "playlists.xhtml";
        }
        return null;
    }

    public String openPlaylist(Playlist playlist) {
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("playlist", playlist);
        return "playlist.xhtml";
    }

    public Playlist getPlaylist() {
        if (playlist == null) {
            playlist = (Playlist) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("playlist");
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("playlist", playlist);
        }
        return playlist;
    }

    public void addToPlaylist(Song song) {
        Playlist playlist = getPlaylist();
        playlist.getSongs().add(song);
        try {
            playlistDAO.update(playlist);
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
    }

    public void removeFromPlaylist(Song song) {
        Playlist playlist = getPlaylist();
        playlist.getSongs().remove(song);
        try {
            playlistDAO.update(playlist);
        } catch (Exception e) {
          errMsg = e.getMessage();
        }
    }

    public List<Song> getAvailableSongs() {
        Playlist playlist = getPlaylist();
        return songDAO.getAll().stream().filter(song -> !playlist.getSongs().contains(song)).toList();
    }
}
