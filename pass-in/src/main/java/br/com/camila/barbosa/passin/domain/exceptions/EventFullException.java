package br.com.camila.barbosa.passin.domain.exceptions;

public class EventFullException extends RuntimeException{
    public EventFullException(String message){
        super(message);
    }
}
