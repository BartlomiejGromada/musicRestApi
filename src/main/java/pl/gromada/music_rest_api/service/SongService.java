package pl.gromada.music_rest_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.gromada.music_rest_api.exception.SongNotFoundException;
import pl.gromada.music_rest_api.model.Song;
import pl.gromada.music_rest_api.repo.SongRepo;

@Service
public class SongService {

    private SongRepo songRepo;

    @Autowired
    public SongService(SongRepo songRepo) {
        this.songRepo = songRepo;
    }

    public Page<Song> findAllSongs(Pageable pageable) {
        return songRepo.findAll(pageable);
    }

    public Song findSongById(long id) {
        return songRepo.findById(id).orElseThrow(() -> new SongNotFoundException(id));
    }

    public void saveSong(Song song) {
        songRepo.save(song);
    }

    public void deleteSongById(long id) {
        songRepo.findById(id).orElseThrow(() -> new SongNotFoundException(id));
        songRepo.deleteById(id);
    }

    public void updateSongById(long id, Song song) {
        Song songById = songRepo.findById(id).orElseThrow(() -> new SongNotFoundException(id));
        song.setIdSong(songById.getIdSong());
        songRepo.save(song);
    }
}
