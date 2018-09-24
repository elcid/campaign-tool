package de.mobile.service;

import de.mobile.service.dto.CampaignHistoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CampaignHistory.
 */
public interface CampaignHistoryService {

    /**
     * Save a campaignHistory.
     *
     * @param campaignHistoryDTO the entity to save
     * @return the persisted entity
     */
    CampaignHistoryDTO save(CampaignHistoryDTO campaignHistoryDTO);

    /**
     * Get all the campaignHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CampaignHistoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" campaignHistory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CampaignHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" campaignHistory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
