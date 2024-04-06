package br.com.camila.barbosa.passin.controllers;

import br.com.camila.barbosa.passin.domain.attendee.Attendee;
import br.com.camila.barbosa.passin.dto.attendee.AttendeeBadgeResponseDTO;
import br.com.camila.barbosa.passin.services.AttendeeService;
import br.com.camila.barbosa.passin.services.CheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeeController {

    private final AttendeeService attendeeService;

    @GetMapping("/{attendeeId}/badge")
    public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        AttendeeBadgeResponseDTO response = this.attendeeService.getAttendeeBadge(attendeeId, uriComponentsBuilder);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity registerCheckIn(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        this.attendeeService.checkInAttendee(attendeeId);
        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();
        return ResponseEntity.created(uri).build();
    }
}
