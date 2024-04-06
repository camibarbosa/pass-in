package br.com.camila.barbosa.passin.repositories;

import br.com.camila.barbosa.passin.domain.attendee.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendeeRepo extends JpaRepository<Attendee, String>{
    List<Attendee> findByEventsId(String eventId);
    Optional<Attendee> findByEventsIdAndEmail(String eventId, String email);
}
