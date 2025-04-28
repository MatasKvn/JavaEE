package org.example.demo.beans;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.example.demo.HeavyCalc;
import org.example.demo.artist.Artist;
import org.example.demo.artist.ArtistDAO;
import org.example.demo.exceptions.HandleBackedBeanExceptions;
import org.example.demo.utils.AppLogger;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

@ViewScoped
@Named
@HandleBackedBeanExceptions
public class ArtistController implements Serializable {

    @Inject
    private ArtistDAO artistDAO;

    @Inject
    private AppLogger logger;

    @Inject
    private HeavyCalc heavyCalc;

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
//            Thread.sleep(10000);
            String calcResult = heavyCalc.heavyCalculation().get();
            logger.log("Heavy calculation done: " + calcResult);
            artistDAO.update(getArtist());
            logger.log("Updated artist " + getArtist().getName());
        } catch (InterruptedException e) {
            // Should never happen
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public String deleteArtist() {
        artistDAO.delete(getArtist().getId());
        return "artists.xhtml";
    }
}
