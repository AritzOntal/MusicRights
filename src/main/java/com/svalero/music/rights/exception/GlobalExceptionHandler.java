package com.svalero.music.rights.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WorkNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(WorkNotFoundException cnfe) {
        return notFound("La obra no existe");
    }

    @ExceptionHandler(MusicianNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(MusicianNotFoundException cnfe) {
        return notFound("El músico no existe");
    }

    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(DocumentNotFoundException cnfe) {
        return notFound("El documento no existe");
    }

    @ExceptionHandler(ClaimNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ClaimNotFoundException cnfe) {
        return notFound("La reclamación no existe");
    }

    @ExceptionHandler(ConcertNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ConcertNotFoundException cnfe) {
        return notFound("El concierto no existe");
    }

    private ResponseEntity<ErrorResponse> notFound(String message) {
        ErrorResponse body = new ErrorResponse(404, "Not-found", message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
