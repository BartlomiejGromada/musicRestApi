package pl.gromada.music_rest_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Singer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSinger;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String nickname;
    @NotBlank
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date dateOfBirth;
    @ManyToMany(mappedBy = "singers")
    @JsonBackReference
    private Set<Song> songs = new HashSet<>();

    @Override
    public String toString() {
        return "Singer{" +
                "idSinger=" + idSinger +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", dateOfBirth=" + dateOfBirth + "}";
    }
}
