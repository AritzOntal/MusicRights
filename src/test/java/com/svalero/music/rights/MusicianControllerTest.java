package com.svalero.music.rights;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.svalero.music.rights.controller.MusicianController;
import com.svalero.music.rights.domain.Claim;
import com.svalero.music.rights.domain.Musician;
import com.svalero.music.rights.domain.Work;
import com.svalero.music.rights.exception.GlobalExceptionHandler;
import com.svalero.music.rights.exception.MusicianNotFoundException;
import com.svalero.music.rights.service.MusicianService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MusicianController.class)
@Import(GlobalExceptionHandler.class)
public class MusicianControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicianService musicianService;
    @Autowired
    private ObjectMapper objectMapper;


    //TODO                                   "/musicians" (GET)

    //404
    @Test
    void return404IfNotExistList() throws Exception {

        Mockito.when(musicianService.findAll(
                Mockito.any(Float.class),
                Mockito.any(Boolean.class),
                Mockito.any(LocalDate.class)
        )).thenThrow(new MusicianNotFoundException()); //any se usa para decirle "cualquier cosa de la Clase X

        mockMvc.perform(get("/musicians")
                        .param("performanceFee", "1.0")
                        .param("affiliated", "true")
                        .param("birthDate", LocalDate.now().toString()))
                .andExpect(status().isNotFound());
    }

    //400
    @Test
    void return400IfBadRquestPost() throws Exception {

        List<Work> works = new ArrayList<>();
        List<Claim> claims = new ArrayList<>();
        LocalDate birthDate = LocalDate.of(1992, 1, 1);

        Musician musician = new Musician();
        musician.setFirstName("");
        musician.setLastName("Sánchez");
        musician.setBirthDate(birthDate);
        musician.setAffiliated(true);
        musician.setDni("12345"); //ERRRO PROVOCADO
        musician.setPerformanceFee(2.6f);
        musician.setAffiliatedNumber(12312L);
        musician.setWorks(works);
        musician.setClaims(claims);

        //Aquí NO dependo del Service porque @Valid ya me lanza error 400.

        mockMvc.perform(post("/musicians")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(musician))) //Lo provoco encajandole en JSON el objeto malo
                .andExpect(status().isBadRequest()); //Se espera respuesta 400
    }

    //200
    @Test
    void return200ifOk() throws Exception {

        Mockito.when(musicianService.findAll(Mockito.any(Float.class), Mockito.any(Boolean.class), Mockito.any(LocalDate.class)))
                .thenReturn(Mockito.mock(ResponseEntity.class));

        mockMvc.perform(get("/musicians"))
                .andExpect(status().isOk());
    }


    //TODO                                       "/musicians" (POST)

    @Test
    void returnOKifCreatedPost() throws Exception {

        List<Work> works = new ArrayList<>();
        List<Claim> claims = new ArrayList<>();
        LocalDate birthDate = LocalDate.of(1992, 1, 1);

        Musician musician = new Musician();
        musician.setFirstName("Pedro");
        musician.setLastName("Sánchez");
        musician.setBirthDate(birthDate);
        musician.setAffiliated(true);
        musician.setDni("45916040J");
        musician.setPerformanceFee(2.6f);
        musician.setAffiliatedNumber(12312L);
        musician.setWorks(works);
        musician.setClaims(claims);

        Mockito.doNothing().when(musicianService).add(Mockito.any(Musician.class));
        //doNothing porque el Service no devuelve nada, pero necesario poruqe le controller depende él

        mockMvc.perform(post("/musicians")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(musician)))
                .andExpect(status().isCreated());
    }


    //TODO                                    "/musicians/{id}" (GET)

    //404
    @Test
    void return404IfNotExist() throws Exception {
        Long notexistId = 1L;

        //Le digo que si el controller llama con ese id, lance una Excepción
        Mockito.when(musicianService.findById(notexistId))
                .thenThrow(new MusicianNotFoundException());

        //Y aquí lo provoco
        mockMvc.perform(get("/musicians/{id}", notexistId))
                .andExpect(status().isNotFound()); //Lo eseperado es que de este error
    }

    //400
    @Test
    void return400IfIdIsInvalid() throws Exception {
        mockMvc.perform(get("/musicians/abc")) // "abc" no puede convertir a Long y genera MethodArgument...
                .andExpect(status().isBadRequest());
        // Lo gestiona el @ControllerAdvice
    }


    //200

    @Test
    void returnOkIfnotProblem() throws Exception {
        Mockito.when(musicianService.findById(Mockito.anyLong()))
                .thenReturn(Mockito.mock(Musician.class));              //"mock" es para recibir un objeto de vuelta y "any" es para un enviar un argumento

        mockMvc.perform(get("/musicians/{id}", 1L))
                .andExpect(status().isOk());
    }


    //TODO                               "/musicians/{id}" (POST)


}

