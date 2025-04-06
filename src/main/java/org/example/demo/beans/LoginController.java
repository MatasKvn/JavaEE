package org.example.demo.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.example.demo.artist.Artist;
import org.example.demo.artist.ArtistDAO;

import java.io.Serializable;

@Named
@SessionScoped
public class LoginController implements Serializable {
    public static final boolean ENABLED = true;

    @Inject
    private ArtistDAO artistDAO;

    @Getter @Setter
    private String name = null;

    @Getter @Setter
    private String lastPage = null;

    @Getter @Setter
    private String msg = null;

    public LoginController() {}

    public String requireLogin(String pageXhtml) {
        if (!ENABLED) return null;
        lastPage = pageXhtml;
        if (name == null) {
            msg = "Please Enter a name";
            return "LoginPage.xhtml";
        }
        return lastPage;
    }

    public String login() {
        Artist artist = artistDAO.getByName(name);
        if (artist == null) {
            msg = "No such Artist";
            return null;
        }
        return lastPage;
    }

}
