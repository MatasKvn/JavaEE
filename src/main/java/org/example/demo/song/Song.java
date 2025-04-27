package org.example.demo.song;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.*;
import lombok.*;
import org.example.demo.artist.Artist;
import org.example.demo.playlist.Playlist;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode @ToString
public class Song implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 255)
    private String name;

    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "release_date")
    private LocalDateTime releaseDate;

    @Column(name = "duration_seconds")
    private int durationSeconds;

    @ManyToOne(targetEntity = Artist.class)
    private Artist artist;

    @ManyToMany(targetEntity = Playlist.class, cascade = CascadeType.ALL)
    @JoinTable(
            name = "playlist_song",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id")
    )
    private List<Playlist> playlists;

    @Version
    private int version;
}
