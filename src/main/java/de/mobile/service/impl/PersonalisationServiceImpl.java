package de.mobile.service.impl;

import de.mobile.service.PersonalisationService;
import de.mobile.domain.Personalisation;
import de.mobile.repository.PersonalisationRepository;
import de.mobile.service.dto.PersonalisationDTO;
import de.mobile.service.mapper.PersonalisationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Personalisation.
 */
@Service
@Transactional
public class PersonalisationServiceImpl implements PersonalisationService {

    private final Logger log = LoggerFactory.getLogger(PersonalisationServiceImpl.class);

    private final PersonalisationRepository personalisationRepository;

    private final PersonalisationMapper personalisationMapper;

    public PersonalisationServiceImpl(PersonalisationRepository personalisationRepository, PersonalisationMapper personalisationMapper) {
        this.personalisationRepository = personalisationRepository;
        this.personalisationMapper = personalisationMapper;
    }

    /**
     * Save a personalisation.
     *
     * @param personalisationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PersonalisationDTO save(PersonalisationDTO personalisationDTO) {
        log.debug("Request to save Personalisation : {}", personalisationDTO);
        Personalisation personalisation = personalisationMapper.toEntity(personalisationDTO);
        personalisation = personalisationRepository.save(personalisation);
        return personalisationMapper.toDto(personalisation);
    }

    /**
     * Get all the personalisations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonalisationDTO> findAll() {
        log.debug("Request to get all Personalisations");
        return personalisationRepository.findAll().stream()
            .map(personalisationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one personalisation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PersonalisationDTO> findOne(Long id) {
        log.debug("Request to get Personalisation : {}", id);
        return personalisationRepository.findById(id)
            .map(personalisationMapper::toDto);
    }

    /**
     * Delete the personalisation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Personalisation : {}", id);
        personalisationRepository.deleteById(id);
    }
}
