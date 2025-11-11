package com.svalero.music.rights.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WorkNotFoundException.class)
    public ResponseEntity<ErrorResponse> WorkHandleException(WorkNotFoundException cnfe) {
        return notFound("La obra no existe");
    }

    @ExceptionHandler(MusicianNotFoundException.class)
    public ResponseEntity<ErrorResponse> MusicianHandleException(MusicianNotFoundException cnfe) {
        return notFound("El músico no existe");
    }

    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<ErrorResponse> DocumentHandleException(DocumentNotFoundException cnfe) {
        return notFound("El documento no existe");
    }

    @ExceptionHandler(ClaimNotFoundException.class)
    public ResponseEntity<ErrorResponse> ClaimHandleException(ClaimNotFoundException cnfe) {
        return notFound("La reclamación no existe");
    }

    @ExceptionHandler(ConcertNotFoundException.class)
    public ResponseEntity<ErrorResponse> ConcertHandleException(ConcertNotFoundException cnfe) {
        return notFound("El concierto no existe");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex) {
        ErrorResponse body = new ErrorResponse(
                500,
                "Bad request",
                "Ha ocurrido un error inesperado"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    //HELPERS
    private ResponseEntity<ErrorResponse> notFound(String message) {
        ErrorResponse body = new ErrorResponse(404, "Not-found", message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

}
