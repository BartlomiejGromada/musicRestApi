package pl.gromada.music_rest_api.api;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gromada.music_rest_api.exception.SongNotFoundException;
import pl.gromada.music_rest_api.model.Song;
import pl.gromada.music_rest_api.repo.SongRepo;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private SongRepo songRepo;

    @Autowired
    public SongController(SongRepo songRepo) {
        this.songRepo = songRepo;
    }

    @GetMapping
    public ResponseEntity<Page<Song>> findAllSongs(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "20") int size) {
        Page<Song> songPage = songRepo.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(songPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findSongById(@PathVariable long id) {
        Song song = songRepo.findById(id).orElseThrow(() -> new SongNotFoundException(id));
        return ResponseEntity.ok(song);
    }
}
