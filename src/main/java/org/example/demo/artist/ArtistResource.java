package org.example.demo.artist;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.List;

@Produces("application/json")
@Path("/artists")
@Stateless
public class ArtistResource {
    @Inject
    private ArtistDAO artistDAO;


    @GET
    public List<Artist> getArtists(long id) {
        return artistDAO.getAll();
    }

    @GET
    @Path("/{id}")
    public Artist getArtist(long id) {
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
    public Artist deleteArtist(long id) {
        return artistDAO.delete(id);
    }
}
