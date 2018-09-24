package de.mobile.repository;

import de.mobile.domain.Personalisation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Personalisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonalisationRepository extends JpaRepository<Personalisation, Long> {

}
