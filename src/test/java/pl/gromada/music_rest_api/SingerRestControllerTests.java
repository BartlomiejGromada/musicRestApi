package pl.gromada.music_rest_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.gromada.music_rest_api.api.SingerRestController;
import pl.gromada.music_rest_api.exception.SingerNotFoundException;
import pl.gromada.music_rest_api.model.Singer;
import pl.gromada.music_rest_api.service.SingerService;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SingerRestController.class)
class SingerRestControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    SingerService singerService;

    @Test
    @DisplayName("Method findAllSingers should return response 200 ok")
    public void find_all_singers_should_return_ok() throws Exception {
        List<Singer> singers = Arrays.asList(
                new Singer(1L, "Bartek", "Kowal", "Bartas",
                        Date.valueOf("2000-01-02"), Collections.emptyList()),
                new Singer(2L, "Filip", "Szcześniak", "Taco",
                        Date.valueOf("1992-04-01"), Collections.emptyList())
        );
        Page<Singer> pageSingers = new PageImpl<>(singers);
        Mockito.when(singerService.findAllSingers(PageRequest.of(0, 20))).thenReturn(pageSingers);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/singers")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Method findSingerById should return response 200 ok")
    public void find_singer_should_return_200_ok() throws Exception {
        Mockito.when(singerService.findSingerById(Mockito.anyLong())).thenReturn(
                new Singer(1L, "Bartek", "Kowal", "Bartas",
                        Date.valueOf("2000-01-02"), Collections.emptyList()));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/singers/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Bartek"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Kowal"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("Bartas"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth").isNotEmpty())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Method findSingerById should return response 404 notFound")
    public void find_singer_by_id_return_404_not_found() throws Exception {
        Mockito.when(singerService.findSingerById(2L)).thenThrow(new SingerNotFoundException(2L));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/singers/{id}", 2L)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Singer with id: 2 doesn't exist."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @DisplayName("Method deleteSingerById should return response 200 ok")
    public void delete_singer_by_id_should_return_200_ok() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/singers/{idSinger}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Method deleteSingerById should return response 404 notFound")
    public void delete_singer_by_id_should_return_404_not_found() throws Exception {
        Mockito.doThrow(new SingerNotFoundException(2L)).when(singerService).deleteSingerById(2L);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/singers/{id}", 2L)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Singer with id: 2 doesn't exist."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty());

    }

    @Test
    @DisplayName("Method saveSinger should return response 201 created")
    public void save_singer_should_return_201_created() throws Exception {
        Singer singer = new Singer(1L, "Filip", "Szcześniak", "Taco",
                Date.valueOf("2000-05-08"), Collections.emptyList());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/singers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(singer)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Method saveSinger should return response 400 bad request (MethodArgumentNotValidException)")
    public void save_singer_should_return_400_bad_request() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/singers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(new Singer())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Method updateSinger should return response 200 ok")
    public void update_singer_should_return_200_ok() throws Exception {
        Singer singer = new Singer(1L, "Filip", "Szcześniak", "Taco",
                Date.valueOf("2000-05-08"), Collections.emptyList());

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/singers/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(singer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Method updateSinger should return response 400 bad request (MethodArgumentNotValidException)")
    public void update_singer_should_return_400_bad_request() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/singers/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new Singer())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
