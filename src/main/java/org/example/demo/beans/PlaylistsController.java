package org.example.demo.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.example.demo.playlist.Playlist;
import org.example.demo.playlist.PlaylistDAO;

import java.io.Serializable;
import java.util.List;

@RequestScoped
@Named
public class PlaylistsController implements Serializable {

    @Inject
    private PlaylistDAO playlistDAO;

    @Getter @Setter
    private String playlistName;

    @Getter @Setter
    private String playlistDescription;

    @Getter @Setter
    private String errMsg;

    public List<Playlist> getPlaylists() {
        return playlistDAO.getAll();
    }

    public void createPlaylist() {
        try {
            Playlist playlist = new Playlist();
            playlist.setName(playlistName);
            playlistDAO.create(playlist);
            playlistName = null;
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
    }

    public void deletePlaylist(Playlist playlist) {
        try {
            playlistDAO.delete(playlist.getId());
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
    }

}
