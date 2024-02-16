package solds.srorage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import solds.model.TicketSave;

@Repository
public interface TicketSaveRepository extends JpaRepository<TicketSave, Long> {
}
