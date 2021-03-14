package pl.gromada.music_rest_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSong;
    @NotBlank(message = "{pl.gromada.model.Song.name.notBlank.message}")
    private String name;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            joinColumns = @JoinColumn(name = "idSong", referencedColumnName = "idSong"),
            inverseJoinColumns = @JoinColumn(name = "idSinger", referencedColumnName = "idSinger")
    )
    private List<Singer> singers = new ArrayList<>();
    @JsonFormat(pattern = "YYYY-MM-dd")
    @NotNull(message = "{pl.gromada.model.Song.releaseDate.notNull.message}")
    private Date releaseDate;


}
