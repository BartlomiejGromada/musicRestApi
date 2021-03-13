package pl.gromada.music_rest_api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gromada.music_rest_api.model.Song;

@Repository
public interface SongRepo extends JpaRepository<Song, Long> {
}
