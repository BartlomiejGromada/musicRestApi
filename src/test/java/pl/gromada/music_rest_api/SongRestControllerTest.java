package pl.gromada.music_rest_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.gromada.music_rest_api.api.SongRestController;
import pl.gromada.music_rest_api.exception.SongNotFoundException;
import pl.gromada.music_rest_api.model.Song;
import pl.gromada.music_rest_api.service.SongService;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@WebMvcTest(SongRestController.class)
public class SongRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SongService songService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Method findAllSongs should return response 200 ok")
    public void find_all_songs_should_return_200_ok() throws Exception {
        List<Song> songs = Arrays.asList(
                new Song(1L, "6 zer", Collections.emptyList(), Date.valueOf("2018-06-11")),
                new Song(2L, "Baby", Collections.emptyList(), Date.valueOf("2015-01-12"))
        );
        Page<Song> pageSongs = new PageImpl<>(songs);
        Mockito.when(songService.findAllSongs(PageRequest.of(0, 20))).thenReturn(pageSongs);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/songs"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Method findSongById should return response 200 ok")
    public void find_song_by_id_should_return_200_ok() throws Exception {
        Mockito.when(songService.findSongById(Mockito.anyLong())).thenReturn(
                new Song(1L, "6 zer", Collections.emptyList(), Date.valueOf("2018-06-11")));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/songs/{id}", 1L))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("6 zer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.releaseDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Method findSongById should return response 404 not found")
    public void find_song_should_return_404_not_found() throws Exception {
        Mockito.doThrow(new SongNotFoundException(1L)).when(songService).findSongById(1L);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/songs/{id}", 1L))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Method deleteSongById should return response 200 ok")
    public void delete_song_by_id_should_return_200_ok() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/songs/{id}", 1L))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Method deleteSongById should return response 404 not found")
    public void delete_song_by_id_should_return_404_not_found() throws Exception {
        Mockito.doThrow(new SongNotFoundException(1L)).when(songService).deleteSongById(1L);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/songs/{id}", 1L))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Method saveSong should return response 201 created")
    public void save_song_should_return_201_created() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/songs")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new Song(1L, "6 zer",
                        Collections.emptyList(), Date.valueOf("2010-10-02")))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Method saveSong should return response 400 bad request (MethodArgumentNotValidException)")
    public void save_song_should_return_400_bad_request() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/songs")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new Song())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Method updateSong should return response 200 ok")
    public void update_song_should_return_200_ok() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/songs/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(
                        new Song(1L, "6 zer", Collections.emptyList(), Date.valueOf("2020-01-05")))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Method updateSong should return response 400 bad request (MethodArgumentNotValidException)")
    public void update_song_should_return_400_bad_request() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/songs/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new Song())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
