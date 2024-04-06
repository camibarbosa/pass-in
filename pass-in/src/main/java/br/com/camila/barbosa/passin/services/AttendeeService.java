package br.com.camila.barbosa.passin.services;

import br.com.camila.barbosa.passin.domain.attendee.Attendee;
import br.com.camila.barbosa.passin.domain.attendee.exceptions.AttendeeAlreadyExistsException;
import br.com.camila.barbosa.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import br.com.camila.barbosa.passin.domain.checkIns.CheckIn;
import br.com.camila.barbosa.passin.dto.attendee.AttendeeBadgeResponseDTO;
import br.com.camila.barbosa.passin.dto.attendee.AttendeeDetails;
import br.com.camila.barbosa.passin.dto.attendee.AttendeesListResponseDTO;
import br.com.camila.barbosa.passin.dto.attendee.AttendeeBadgeDTO;
import br.com.camila.barbosa.passin.repositories.AttendeeRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendeeService {
    private final AttendeeRepo attendeeRepo;

    private final CheckInService checkInService;

    public AttendeeService(AttendeeRepo attendeeRepo, CheckInService checkInService) {
        this.attendeeRepo = attendeeRepo;
        this.checkInService = checkInService;
    }

    public List<Attendee> getAllAttendeesFromEvent(String eventId){
       return this.attendeeRepo.findByEventsId(eventId);
    }

    public AttendeesListResponseDTO getEventsAttendee(String eventId){
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInService.getCheckIn(attendee.getId());
            LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(),attendee.getCreatedAt(),checkedInAt);
        }).toList();
        return new AttendeesListResponseDTO(attendeeDetailsList);
    }

    public void verifyAttendeeSubscription(String email, String eventId){
        Optional<Attendee> isAttendeeRegistered = this.attendeeRepo.findByEventsIdAndEmail(eventId, email);
        if(isAttendeeRegistered.isPresent()) throw new AttendeeAlreadyExistsException("Attendee is already registered");
    }

    public Attendee registerAttendee(Attendee newAttendee){
        this.attendeeRepo.save(newAttendee);
        return newAttendee;
    }

    public void checkInAttendee(String attendeeId){
        Attendee attendee = this.getAttendee(attendeeId);
        this.checkInService.registerCheckIn(attendee);
    }

    private Attendee getAttendee(String attendeeId){
        return this.attendeeRepo.findById(attendeeId).orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with ID: " + attendeeId));
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        Attendee attendee = this.getAttendee(attendeeId);
        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();

        AttendeeBadgeDTO badgeDTO = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri, attendee.getEvents().getId());
        return new AttendeeBadgeResponseDTO(badgeDTO);
    }
}
