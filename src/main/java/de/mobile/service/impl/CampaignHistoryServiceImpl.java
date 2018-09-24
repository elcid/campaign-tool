package de.mobile.service.impl;

import de.mobile.service.CampaignHistoryService;
import de.mobile.domain.CampaignHistory;
import de.mobile.repository.CampaignHistoryRepository;
import de.mobile.service.dto.CampaignHistoryDTO;
import de.mobile.service.mapper.CampaignHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CampaignHistory.
 */
@Service
@Transactional
public class CampaignHistoryServiceImpl implements CampaignHistoryService {

    private final Logger log = LoggerFactory.getLogger(CampaignHistoryServiceImpl.class);

    private final CampaignHistoryRepository campaignHistoryRepository;

    private final CampaignHistoryMapper campaignHistoryMapper;

    public CampaignHistoryServiceImpl(CampaignHistoryRepository campaignHistoryRepository, CampaignHistoryMapper campaignHistoryMapper) {
        this.campaignHistoryRepository = campaignHistoryRepository;
        this.campaignHistoryMapper = campaignHistoryMapper;
    }

    /**
     * Save a campaignHistory.
     *
     * @param campaignHistoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CampaignHistoryDTO save(CampaignHistoryDTO campaignHistoryDTO) {
        log.debug("Request to save CampaignHistory : {}", campaignHistoryDTO);
        CampaignHistory campaignHistory = campaignHistoryMapper.toEntity(campaignHistoryDTO);
        campaignHistory = campaignHistoryRepository.save(campaignHistory);
        return campaignHistoryMapper.toDto(campaignHistory);
    }

    /**
     * Get all the campaignHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CampaignHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CampaignHistories");
        return campaignHistoryRepository.findAll(pageable)
            .map(campaignHistoryMapper::toDto);
    }


    /**
     * Get one campaignHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CampaignHistoryDTO> findOne(Long id) {
        log.debug("Request to get CampaignHistory : {}", id);
        return campaignHistoryRepository.findById(id)
            .map(campaignHistoryMapper::toDto);
    }

    /**
     * Delete the campaignHistory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CampaignHistory : {}", id);
        campaignHistoryRepository.deleteById(id);
    }
}
