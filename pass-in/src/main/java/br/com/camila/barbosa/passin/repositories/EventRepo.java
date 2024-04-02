package br.com.camila.barbosa.passin.repositories;

import br.com.camila.barbosa.passin.domain.events.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepo extends JpaRepository<Events, String> {
}
