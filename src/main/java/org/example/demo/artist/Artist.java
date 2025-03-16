package org.example.demo.artist;

import jakarta.persistence.*;
import lombok.*;
import org.example.demo.song.Song;

import java.util.List;

@Entity
@Table(indexes = {
        @Index(name = "idx_artist_name", columnList = "name", unique = true)
})
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode @ToString
public class Artist {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 255)
    private String name;

    private String about;

    @OneToMany(targetEntity = Song.class)
    private List<Song> songs;
}
