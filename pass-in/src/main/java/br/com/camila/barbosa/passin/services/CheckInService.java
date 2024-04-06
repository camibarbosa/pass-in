package br.com.camila.barbosa.passin.services;

import br.com.camila.barbosa.passin.domain.attendee.Attendee;
import br.com.camila.barbosa.passin.domain.checkIns.CheckIn;
import br.com.camila.barbosa.passin.domain.checkIns.exception.CheckInAlreadyExistsException;
import br.com.camila.barbosa.passin.repositories.CheckInRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {
    private final CheckInRepo checkInRepo;

    public void registerCheckIn(Attendee attendee){
        this.verifyCheckInExists(attendee.getId());

        CheckIn newCheckIn = new CheckIn();
        newCheckIn.setAttendee(attendee);
        newCheckIn.setCreatedAt(LocalDateTime.now());

        this.checkInRepo.save(newCheckIn);
    }

    private void verifyCheckInExists(String attendeeId){
        Optional<CheckIn> isCheckedIn = this.getCheckIn(attendeeId);
        if(isCheckedIn.isPresent()) throw new CheckInAlreadyExistsException("Attendee already checked in.");
    }

    public Optional<CheckIn> getCheckIn(String attendeeId){
        return this.checkInRepo.findByAttendeeId(attendeeId);
    }
}
