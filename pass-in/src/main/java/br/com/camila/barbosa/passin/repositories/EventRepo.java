package br.com.camila.barbosa.passin.repositories;

import br.com.camila.barbosa.passin.domain.events.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepo extends JpaRepository<Event, String> {
}
