package pl.gromada.music_rest_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Singer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSinger;
    @NotBlank(message = "{pl.gromada.model.Singer.firstName.notBlank.message}")
    private String firstName;
    @NotBlank(message = "{pl.gromada.model.Singer.lastName.notBlank.message}")
    private String lastName;
    private String nickname;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{pl.gromada.model.Singer.dateOfBirth.notNull.message}")
    private Date dateOfBirth;
    @ManyToMany(mappedBy = "singers", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private List<Song> songs = new ArrayList<>();

    @Override
    public String toString() {
        return "Singer{" +
                "idSinger=" + idSinger +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", dateOfBirth=" + dateOfBirth + "}";
    }

    public void removeSong(List<Song> songs) {
        for (Song song : songs)
            song.getSingers().remove(this);

        this.getSongs().clear();
    }
}
