package de.mobile.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.mobile.service.PersonalisationService;
import de.mobile.web.rest.errors.BadRequestAlertException;
import de.mobile.web.rest.util.HeaderUtil;
import de.mobile.service.dto.PersonalisationDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Personalisation.
 */
@RestController
@RequestMapping("/api")
public class PersonalisationResource {

    private final Logger log = LoggerFactory.getLogger(PersonalisationResource.class);

    private static final String ENTITY_NAME = "personalisation";

    private final PersonalisationService personalisationService;

    public PersonalisationResource(PersonalisationService personalisationService) {
        this.personalisationService = personalisationService;
    }

    /**
     * POST  /personalisations : Create a new personalisation.
     *
     * @param personalisationDTO the personalisationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personalisationDTO, or with status 400 (Bad Request) if the personalisation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personalisations")
    @Timed
    public ResponseEntity<PersonalisationDTO> createPersonalisation(@RequestBody PersonalisationDTO personalisationDTO) throws URISyntaxException {
        log.debug("REST request to save Personalisation : {}", personalisationDTO);
        if (personalisationDTO.getId() != null) {
            throw new BadRequestAlertException("A new personalisation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonalisationDTO result = personalisationService.save(personalisationDTO);
        return ResponseEntity.created(new URI("/api/personalisations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personalisations : Updates an existing personalisation.
     *
     * @param personalisationDTO the personalisationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personalisationDTO,
     * or with status 400 (Bad Request) if the personalisationDTO is not valid,
     * or with status 500 (Internal Server Error) if the personalisationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personalisations")
    @Timed
    public ResponseEntity<PersonalisationDTO> updatePersonalisation(@RequestBody PersonalisationDTO personalisationDTO) throws URISyntaxException {
        log.debug("REST request to update Personalisation : {}", personalisationDTO);
        if (personalisationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonalisationDTO result = personalisationService.save(personalisationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personalisationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personalisations : get all the personalisations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personalisations in body
     */
    @GetMapping("/personalisations")
    @Timed
    public List<PersonalisationDTO> getAllPersonalisations() {
        log.debug("REST request to get all Personalisations");
        return personalisationService.findAll();
    }

    /**
     * GET  /personalisations/:id : get the "id" personalisation.
     *
     * @param id the id of the personalisationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personalisationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/personalisations/{id}")
    @Timed
    public ResponseEntity<PersonalisationDTO> getPersonalisation(@PathVariable Long id) {
        log.debug("REST request to get Personalisation : {}", id);
        Optional<PersonalisationDTO> personalisationDTO = personalisationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personalisationDTO);
    }

    /**
     * DELETE  /personalisations/:id : delete the "id" personalisation.
     *
     * @param id the id of the personalisationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personalisations/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonalisation(@PathVariable Long id) {
        log.debug("REST request to delete Personalisation : {}", id);
        personalisationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
