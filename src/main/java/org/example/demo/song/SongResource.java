package org.example.demo.song;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.List;

@Produces("application/json")
@Path("/songs")
@Stateless
public class SongResource {
    @Inject
    private SongDAO songDAO;

    public SongResource() {}

    @GET
    public List<Song> getSongs() {
        return songDAO.getAll();
    }

    @GET
    @Path("/{id}")
    public Song getSong(@PathParam("id") long id) {
        return songDAO.getById(id);
    }

    @POST @Consumes("application/json")
    public Song createSong(Song song) {
        return songDAO.create(song);
    }

    @PUT @Consumes("application/json")
    public Song editSong(Song song) {
        return songDAO.update(song);
    }

    @DELETE
    @Path("/{id}")
    public Song deleteSong(@PathParam("id") long id) {
        return songDAO.delete(id);
    }
}
