package de.mobile.repository;

import de.mobile.domain.CampaignHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CampaignHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampaignHistoryRepository extends JpaRepository<CampaignHistory, Long> {

}
