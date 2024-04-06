package br.com.camila.barbosa.passin.domain.attendee.exceptions;

public class AttendeeNotFoundException extends RuntimeException{
    public AttendeeNotFoundException(String message){
        super(message);
    }
}
