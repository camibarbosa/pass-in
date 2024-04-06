package br.com.camila.barbosa.passin.config;

import br.com.camila.barbosa.passin.domain.attendee.exceptions.AttendeeAlreadyExistsException;
import br.com.camila.barbosa.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import br.com.camila.barbosa.passin.domain.checkIns.exception.CheckInAlreadyExistsException;
import br.com.camila.barbosa.passin.domain.exceptions.EventFullException;
import br.com.camila.barbosa.passin.domain.exceptions.EventNotFoundException;
import br.com.camila.barbosa.passin.dto.general.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFound(EventNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(EventFullException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventFull(EventFullException exception){
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity handleAttendeeNotFound(AttendeeNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeAlreadyExistsException.class)
    public ResponseEntity handleAttendeeAlreadyExists(AttendeeAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(CheckInAlreadyExistsException.class)
    public ResponseEntity handleCheckInAlreadyExists(CheckInAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
