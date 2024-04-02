package br.com.camila.barbosa.passin.repositories;

import br.com.camila.barbosa.passin.domain.attendee.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendeeRepo extends JpaRepository<Attendee, String> {
}
