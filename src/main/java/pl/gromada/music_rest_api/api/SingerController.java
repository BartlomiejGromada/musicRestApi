package pl.gromada.music_rest_api.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.gromada.music_rest_api.exception.SingerNotFoundException;
import pl.gromada.music_rest_api.model.Singer;
import pl.gromada.music_rest_api.repo.SingerRepo;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/singers")
public class SingerController {

    private SingerRepo singerRepo;

    @Autowired
    public SingerController(SingerRepo singerRepo) {
        this.singerRepo = singerRepo;
    }

    @GetMapping
    public ResponseEntity<Page<Singer>> findAllSingers(Pageable pageable) {
        Page<Singer> singerPage = singerRepo.findAll(pageable);
        return ResponseEntity.ok(singerPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findSingerById(@PathVariable long id) {
        Singer singer = singerRepo.findById(id).orElseThrow(() -> new SingerNotFoundException(id));
        return ResponseEntity.ok(singer);
    }

    @PostMapping
    public ResponseEntity<?> addSinger(@Valid @RequestBody Singer singer) {
        singerRepo.save(singer);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(singer.getIdSinger())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSingerById(@PathVariable long id) {
        Singer singer = singerRepo.findById(id).orElseThrow(() -> new SingerNotFoundException(id));
        singer.removeSong(singer.getSongs());
        singerRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSingerById(@PathVariable long id, @Valid @RequestBody Singer singer) {
        Singer singerById = singerRepo.findById(id).orElseThrow(() -> new SingerNotFoundException(id));
        singer.setIdSinger(singerById.getIdSinger());
        singerRepo.save(singer);
        return ResponseEntity.ok().build();
    }
}
