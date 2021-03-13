package pl.gromada.music_rest_api.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gromada.music_rest_api.exception.SingerNotFoundException;
import pl.gromada.music_rest_api.model.Singer;
import pl.gromada.music_rest_api.repo.SingerRepo;

@RestController
@RequestMapping("/api/singers")
public class SingerController {

    private SingerRepo singerRepo;

    @Autowired
    public SingerController(SingerRepo singerRepo) {
        this.singerRepo = singerRepo;
    }

    @GetMapping
    public ResponseEntity<Page<Singer>> findAllSingers(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "20") int size,
                                                       @RequestParam(defaultValue = "idSinger,ASC", required = false) String[] sort) {
        Sort sortBy = Sort.by(sort[0]).ascending();
        if (sort[1].trim().equalsIgnoreCase("desc"))
            sortBy = sortBy.descending();

        Page<Singer> singerPage = singerRepo.findAll(PageRequest.of(page, size, sortBy));
        return ResponseEntity.ok(singerPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findSingerById(@PathVariable long id) {
        Singer singer = singerRepo.findById(id).orElseThrow(() -> new SingerNotFoundException(id));
        return ResponseEntity.ok(singer);
    }
}
