package br.com.camila.barbosa.passin.domain.attendee.exceptions;

public class AttendeeAlreadyExistsException extends RuntimeException {
    public AttendeeAlreadyExistsException(String message){
        super(message);
    }
}
