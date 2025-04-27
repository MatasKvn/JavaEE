package org.example.demo.beans;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.example.demo.artist.Artist;
import org.example.demo.artist.ArtistDAO;
import org.example.demo.exceptions.HandleBackedBeanExceptions;
import org.example.demo.utils.AppLogger;

import java.io.Serializable;

@ViewScoped
@Named
@HandleBackedBeanExceptions
public class ArtistController implements Serializable {

    @Inject
    private ArtistDAO artistDAO;

    @Inject
    private AppLogger logger;

    @Setter
    private Artist artist = null;

    @Getter @Setter private String errorMsg = "";

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

    // OptimisticLockException demonstration
    public Artist updateArtist() {
        try {
            logger.log("Updating artist " + getArtist().getName());
            Thread.sleep(10000);
            artistDAO.update(getArtist());
            logger.log("Updated artist " + getArtist().getName());
        } catch (InterruptedException e) {
            // Should never happen
        }
        return null;
    }

    public String deleteArtist() {
        artistDAO.delete(getArtist().getId());
        return "artists.xhtml";
    }
}
