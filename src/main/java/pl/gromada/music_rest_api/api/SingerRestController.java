package pl.gromada.music_rest_api.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.gromada.music_rest_api.model.Singer;
import pl.gromada.music_rest_api.service.SingerService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/singers")
public class SingerRestController {

    private SingerService singerService;

    @Autowired
    public SingerRestController(SingerService singerService) {
        this.singerService = singerService;
    }

    @GetMapping
    public ResponseEntity<Page<Singer>> findAllSingers(Pageable pageable) {
        Page<Singer> singerPage = singerService.findAllSingers(pageable);
        return ResponseEntity.ok(singerPage);
    }

    @ApiOperation(value = "Find singer by id", notes = "provide information about singer by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> findSingerById(@ApiParam(value = "unique id of singer", example = "123") @PathVariable long id) {
        Singer singer = singerService.findSingerById(id);
        return ResponseEntity.ok(singer);
    }

    @PostMapping
    public ResponseEntity<?> addSinger(@Valid @RequestBody Singer singer) {
        singerService.saveSinger(singer);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(singer.getIdSinger())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSingerById(@PathVariable long id) {
        singerService.deleteSingerById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSingerById(@PathVariable long id, @Valid @RequestBody Singer singer) {
        singerService.updateSingerById(id, singer);
        return ResponseEntity.ok().build();
    }
}
