package pl.gromada.music_rest_api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gromada.music_rest_api.model.Singer;

@Repository
public interface SingerRepo extends JpaRepository<Singer, Long> {
}
