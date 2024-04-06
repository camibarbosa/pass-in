package br.com.camila.barbosa.passin.dto.event;

public record EventRequestDTO(
        String title,
        String details,
        Integer maximumAttendees
){}
