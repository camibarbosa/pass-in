package br.com.camila.barbosa.passin.services;

import br.com.camila.barbosa.passin.domain.attendee.Attendee;
import br.com.camila.barbosa.passin.domain.exceptions.EventNotFoundException;
import br.com.camila.barbosa.passin.dto.EventResponseDTO;
import br.com.camila.barbosa.passin.domain.events.Event;
import br.com.camila.barbosa.passin.dto.EventIdDTO;
import br.com.camila.barbosa.passin.dto.EventRequestDTO;
import br.com.camila.barbosa.passin.repositories.AttendeeRepo;
import br.com.camila.barbosa.passin.repositories.EventRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepo eventRepo;
    private final AttendeeService attendeeService;

    public EventResponseDTO getEventDetail(String eventId){
        Event event = this.eventRepo.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with id" + eventId));
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);
        return new EventResponseDTO(event, attendeeList.size());
    }
    public EventIdDTO createEvent(EventRequestDTO eventDTO){
        Event newEvent = new Event();
        newEvent.setTitle(eventDTO.title());
        newEvent.setDetails(eventDTO.details());
        newEvent.setMaximumAttendees(eventDTO.maximumAttendees());
        newEvent.setSlug(this.createSlug(eventDTO.title()));

        this.eventRepo.save(newEvent);

        return new EventIdDTO(newEvent.getId());

    }
    private String createSlug(String text){
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }
}