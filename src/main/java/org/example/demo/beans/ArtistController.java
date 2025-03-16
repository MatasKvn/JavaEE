package org.example.demo.beans;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Setter;
import org.example.demo.artist.Artist;
import org.example.demo.artist.ArtistDAO;

import java.io.Serializable;

@ViewScoped
@Named
public class ArtistController implements Serializable {

    @Inject
    ArtistDAO artistDAO;

    @Setter
    private Artist artist = null;

    public Artist getArtist() {
        if (artist == null) {
            artist = (Artist) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("artist");
        }
        return artist;
    }

    public String openArtistView(Artist artist) {
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("artist", artist);
        return "EditArtistForm.xhtml";
    }

    public void updateArtist() {
        artistDAO.update(getArtist());
    }

    public String deleteArtist() {
        artistDAO.delete(getArtist().getId());
        return "artists.xhtml";
    }
}
