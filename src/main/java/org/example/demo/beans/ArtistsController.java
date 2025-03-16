package org.example.demo.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.example.demo.artist.Artist;
import org.example.demo.artist.ArtistDAO;

import java.util.List;

@RequestScoped
@Named
public class ArtistsController {
    @Inject
    private ArtistDAO artistDAO;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String about;

    @Getter @Setter
    private String errMsg;

    public void create() {
        Artist artist = new Artist();
        if (name == null || name.isBlank()) {
            errMsg = "Name is required";
            return;
        }
        if (about == null || about.isBlank()) {
            errMsg = "About is required";
            return;
        }
        artist.setName(name);
        artist.setAbout(about);
        try {
            artistDAO.create(artist);
        } catch (Exception e) {
            errMsg = "Error creating artist";
            return;
        }
    }

    public List<Artist> getArtists() {
        return artistDAO.getAll();
    }
}
