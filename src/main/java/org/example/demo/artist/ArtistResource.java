package org.example.demo.artist;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.List;
import java.util.logging.Logger;

@Produces("application/json")
@Path("/artists")
@Stateless
public class ArtistResource {
    @Inject
    private ArtistDAO artistDAO;

    @GET
    public List<Artist> getArtists() {
        return artistDAO.getAll();
    }

    @GET
    @Path("/{id}")
    public Artist getArtist(@PathParam("id") long id) {
        Logger.getLogger(this.getClass().getName()).warning("Called get with id: " + id);
        return artistDAO.getById(id);
    }

    @POST
    public Artist createArtist(Artist artist) {
        return artistDAO.create(artist);
    }

    @PUT
    public Artist updateArtist(Artist artist) {
        return artistDAO.update(artist);
    }

    @DELETE
    @Path("/{id}")
    public Artist deleteArtist(@PathParam("id") long id) {
        return artistDAO.delete(id);
    }
}
