package br.com.camila.barbosa.passin.repositories;

import br.com.camila.barbosa.passin.domain.attendee.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendeeRepo extends JpaRepository<Attendee, String>{
    List<Attendee> findByEventsId(String eventId);
}
