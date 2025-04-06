package org.example.demo.beans;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import lombok.Setter;
import org.example.demo.artist.Artist;
import org.example.demo.artist.ArtistDAO;

import java.io.Serializable;
import java.util.logging.Logger;

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

    // OptimisticLockException demonstration
    public void updateArtist() {
        try {
            Logger.getLogger(this.getClass().getName()).warning("... Updating artist " + getArtist().getName());
            Thread.sleep(10000);
            artistDAO.update(getArtist());
            Logger.getLogger(this.getClass().getName()).warning("+++ Updated artist " + getArtist().getName());
        } catch (OptimisticLockException e) {
            Logger.getLogger(this.getClass().getName()).warning("Optimistic lock exception occurred");
        } catch (InterruptedException e) {
            Logger.getLogger(this.getClass().getName()).warning("Interrupted");
        }
    }

    public String deleteArtist() {
        artistDAO.delete(getArtist().getId());
        return "artists.xhtml";
    }
}
