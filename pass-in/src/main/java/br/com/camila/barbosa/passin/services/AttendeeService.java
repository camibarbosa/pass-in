package br.com.camila.barbosa.passin.services;

import br.com.camila.barbosa.passin.domain.attendee.Attendee;
import br.com.camila.barbosa.passin.domain.checkIns.CheckIn;
import br.com.camila.barbosa.passin.dto.attendee.AttendeeDetails;
import br.com.camila.barbosa.passin.dto.attendee.AttendeesListResponseDTO;
import br.com.camila.barbosa.passin.repositories.AttendeeRepo;
import br.com.camila.barbosa.passin.repositories.CheckInRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {
    private AttendeeRepo attendeeRepo;
    private CheckInRepo checkInRepo;

    public List<Attendee> getAllAttendeesFromEvent(String eventId){
       return this.attendeeRepo.findByEventsId(eventId);
    }

    public AttendeesListResponseDTO getEventsAttendee(String eventId){
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInRepo.findByAttendeeId(attendee.getId());
            LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(),attendee.getCreatedAt(),checkedInAt);
        }).toList();
        return new AttendeesListResponseDTO(attendeeDetailsList);
    }
}
