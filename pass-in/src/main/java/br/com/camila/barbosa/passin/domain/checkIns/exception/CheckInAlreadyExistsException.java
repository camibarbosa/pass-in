package br.com.camila.barbosa.passin.domain.checkIns.exception;

public class CheckInAlreadyExistsException extends RuntimeException {
    public CheckInAlreadyExistsException(String message){
        super(message);
    }
}
