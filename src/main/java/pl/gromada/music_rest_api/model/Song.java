package pl.gromada.music_rest_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @NotBlank
    private String name;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(name = "idSong", referencedColumnName = "idSong"),
            inverseJoinColumns = @JoinColumn(name = "idSinger", referencedColumnName = "idSinger")
    )
    private List<Singer> singers = new ArrayList<>();
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date releaseDate;


}
