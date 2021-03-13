package pl.gromada.music_rest_api.exception;

public class SongNotFoundException extends RuntimeException {

    public SongNotFoundException(long id) {
        super("Song with id: " + id + " doesn't exist.");
    }
}
