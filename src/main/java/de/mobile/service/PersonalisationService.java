package de.mobile.service;

import de.mobile.service.dto.PersonalisationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Personalisation.
 */
public interface PersonalisationService {

    /**
     * Save a personalisation.
     *
     * @param personalisationDTO the entity to save
     * @return the persisted entity
     */
    PersonalisationDTO save(PersonalisationDTO personalisationDTO);

    /**
     * Get all the personalisations.
     *
     * @return the list of entities
     */
    List<PersonalisationDTO> findAll();


    /**
     * Get the "id" personalisation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PersonalisationDTO> findOne(Long id);

    /**
     * Delete the "id" personalisation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
