package br.com.camila.barbosa.passin.services;

import br.com.camila.barbosa.passin.domain.attendee.Attendee;
import br.com.camila.barbosa.passin.domain.exceptions.EventFullException;
import br.com.camila.barbosa.passin.domain.exceptions.EventNotFoundException;
import br.com.camila.barbosa.passin.dto.event.EventResponseDTO;
import br.com.camila.barbosa.passin.domain.events.Event;
import br.com.camila.barbosa.passin.dto.event.EventIdDTO;
import br.com.camila.barbosa.passin.dto.event.EventRequestDTO;
import br.com.camila.barbosa.passin.dto.attendee.AttendeeIdDTO;
import br.com.camila.barbosa.passin.dto.attendee.AttendeeRequestDTO;
import br.com.camila.barbosa.passin.repositories.EventRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;
    private final AttendeeService attendeeService;

    public EventResponseDTO getEventDetail(String eventId){
        Event event = this.getEventById(eventId);
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

    public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeeRequestDTO attendeeRequestDTO){
        this.attendeeService.verifyAttendeeSubscription(attendeeRequestDTO.email(), eventId);

        Event event = this.getEventById(eventId);
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);

        if(event.getMaximumAttendees() <= attendeeList.size()) throw new EventFullException("Event is full");

        Attendee newAttendee = new Attendee();
        newAttendee.setName(attendeeRequestDTO.name());
        newAttendee.setEmail(attendeeRequestDTO.email());
        newAttendee.setEvents(event);
        newAttendee.setCreatedAt(LocalDateTime.now());
        this.attendeeService.registerAttendee(newAttendee);

        return new AttendeeIdDTO(newAttendee.getId());
    }

    private Event getEventById(String eventId){
        return this.eventRepo.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with id" + eventId));
    }

    private String createSlug(String text){
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }
}