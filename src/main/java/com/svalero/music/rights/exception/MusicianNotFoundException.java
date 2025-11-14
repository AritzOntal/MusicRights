package com.svalero.music.rights.exception;


public class MusicianNotFoundException extends RuntimeException {
    public MusicianNotFoundException() {
        super();
    }

    public MusicianNotFoundException(String message) {
        super(message);
    }
}
