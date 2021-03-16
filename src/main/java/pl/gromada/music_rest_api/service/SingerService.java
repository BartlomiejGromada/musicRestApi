package pl.gromada.music_rest_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.gromada.music_rest_api.exception.SingerNotFoundException;
import pl.gromada.music_rest_api.model.Singer;
import pl.gromada.music_rest_api.repo.SingerRepo;

@Service
public class SingerService {

    private SingerRepo singerRepo;

    @Autowired
    public SingerService(SingerRepo singerRepo) {
        this.singerRepo = singerRepo;
    }

    public Page<Singer> findAllSingers(Pageable pageable) {
        return singerRepo.findAll(pageable);
    }

    public Singer findSingerById(long id) {
        return singerRepo.findById(id).orElseThrow(() -> new SingerNotFoundException(id));
    }

    public void saveSinger(Singer singer) {
        singerRepo.save(singer);
    }

    public void deleteSingerById(long id) {
        Singer singer = singerRepo.findById(id).orElseThrow(() -> new SingerNotFoundException(id));
        singer.removeSong(singer.getSongs());
        singerRepo.deleteById(id);
    }

    public void updateSingerById(long id, Singer singer) {
        Singer singerById = singerRepo.findById(id).orElseThrow(() -> new SingerNotFoundException(id));
        singer.setIdSinger(singerById.getIdSinger());
        singerRepo.save(singer);
    }
}
