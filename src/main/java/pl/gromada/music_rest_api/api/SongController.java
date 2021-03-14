package pl.gromada.music_rest_api.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.gromada.music_rest_api.exception.SongNotFoundException;
import pl.gromada.music_rest_api.model.Song;
import pl.gromada.music_rest_api.repo.SongRepo;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private SongRepo songRepo;

    @Autowired
    public SongController(SongRepo songRepo) {
        this.songRepo = songRepo;
    }

    @GetMapping
    public ResponseEntity<Page<Song>> findAllSongs(Pageable pageable) {
        Page<Song> songPage = songRepo.findAll(pageable);
        return ResponseEntity.ok(songPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findSongById(@PathVariable long id) {
        Song song = songRepo.findById(id).orElseThrow(() -> new SongNotFoundException(id));
        return ResponseEntity.ok(song);
    }

    @PostMapping
    public ResponseEntity<?> addSong(@Valid @RequestBody Song song) throws URISyntaxException {
        songRepo.save(song);
        return ResponseEntity.created(new URI(ServletUriComponentsBuilder.fromCurrentContextPath()
                .buildAndExpand(song.getIdSong()).toUriString())).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSongById(@PathVariable long id) {
        songRepo.findById(id).orElseThrow(() -> new SongNotFoundException(id));
        songRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSong(@PathVariable long id, @Valid @RequestBody Song song) {
        Song songById = songRepo.findById(id).orElseThrow(() -> new SongNotFoundException(id));
        song.setIdSong(songById.getIdSong());
        songRepo.save(song);
        return ResponseEntity.ok().build();
    }

}
