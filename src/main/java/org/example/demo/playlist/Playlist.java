package org.example.demo.playlist;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.demo.song.Song;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter @Setter @EqualsAndHashCode @ToString
public class Playlist {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 255)
    private String name;

    private String description;

    @ManyToMany(targetEntity = Song.class, mappedBy = "playlists")
    private List<Song> songs;

    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Version
    private int version;
}
