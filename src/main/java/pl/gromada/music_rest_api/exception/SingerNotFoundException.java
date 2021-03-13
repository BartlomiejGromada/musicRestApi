package pl.gromada.music_rest_api.exception;

public class SingerNotFoundException extends RuntimeException  {

    public SingerNotFoundException(long id) {
        super("Singer with id: " + id + " doesn't exist.");
    }
}
