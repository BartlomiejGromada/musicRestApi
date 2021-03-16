package pl.gromada.music_rest_api.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.gromada.music_rest_api.model.Song;
import pl.gromada.music_rest_api.service.SongService;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/songs")
public class SongRestController {

    private SongService songService;

    @Autowired
    public SongRestController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public ResponseEntity<Page<Song>> findAllSongs(Pageable pageable) {
        Page<Song> songPage = songService.findAllSongs(pageable);
        return ResponseEntity.ok(songPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findSongById(@PathVariable long id) {
        Song song = songService.findSongById(id);
        return ResponseEntity.ok(song);
    }

    @PostMapping
    public ResponseEntity<?> addSong(@Valid @RequestBody Song song) throws URISyntaxException {
        songService.saveSong(song);
        return ResponseEntity.created(new URI(ServletUriComponentsBuilder.fromCurrentContextPath()
                .buildAndExpand(song.getIdSong()).toUriString())).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSongById(@PathVariable long id) {
        songService.deleteSongById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSongById(@PathVariable long id, @Valid @RequestBody Song song) {
        songService.updateSongById(id, song);
        return ResponseEntity.ok().build();
    }

}
