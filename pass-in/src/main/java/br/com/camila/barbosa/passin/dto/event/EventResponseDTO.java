package br.com.camila.barbosa.passin.dto.event;

import br.com.camila.barbosa.passin.domain.events.Event;
import lombok.Getter;

@Getter
public class EventResponseDTO {

    EventDetailDTO event;

    public EventResponseDTO(Event event, Integer numberOfAttendees){
        this.event = new EventDetailDTO(
                event.getId(),
                event.getTitle(),
                event.getDetails(),
                event.getSlug(),
                event.getMaximumAttendees(),
                numberOfAttendees
        );
    }
}
