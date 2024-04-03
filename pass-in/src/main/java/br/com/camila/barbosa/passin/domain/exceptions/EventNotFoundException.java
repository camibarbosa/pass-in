package br.com.camila.barbosa.passin.domain.exceptions;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String message){
        super(message);
    }
}
