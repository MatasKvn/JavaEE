package org.example.demo.song;

import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import org.example.demo.exceptions.ValidationException;

@Decorator
public abstract class SongDaoValidationDecorator implements SongDAO {

    @Inject
    @Delegate
    private SongDAO songDao;

    @SneakyThrows
    @Override
    public Song create(Song song) {
        if (song.getDurationSeconds() <= 0) {
            throw new ValidationException(Song.class, "durationSeconds", "Duration must be longer than 0 seconds.");
        }
        return songDao.create(song);
    }
}
