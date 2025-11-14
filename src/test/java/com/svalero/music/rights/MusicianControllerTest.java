package com.svalero.music.rights;

import com.svalero.music.rights.controller.MusicianController;
import com.svalero.music.rights.exception.GlobalExceptionHandler;
import com.svalero.music.rights.exception.MusicianNotFoundException;
import com.svalero.music.rights.service.MusicianService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MusicianController.class)
@Import(GlobalExceptionHandler.class)
public class MusicianControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicianService musicianService;


    @Test
    void return404IfNotExist() throws Exception {
        Long notexistId = 9999L;

        Mockito.when(musicianService.findById(notexistId))
                .thenThrow(new MusicianNotFoundException());

        mockMvc.perform(get("/musicians/{id}", notexistId))
                .andExpect(status().isNotFound());
    }
}
