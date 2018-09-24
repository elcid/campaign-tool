package de.mobile.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.mobile.service.CampaignHistoryService;
import de.mobile.web.rest.errors.BadRequestAlertException;
import de.mobile.web.rest.util.HeaderUtil;
import de.mobile.web.rest.util.PaginationUtil;
import de.mobile.service.dto.CampaignHistoryDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CampaignHistory.
 */
@RestController
@RequestMapping("/api")
public class CampaignHistoryResource {

    private final Logger log = LoggerFactory.getLogger(CampaignHistoryResource.class);

    private static final String ENTITY_NAME = "campaignHistory";

    private final CampaignHistoryService campaignHistoryService;

    public CampaignHistoryResource(CampaignHistoryService campaignHistoryService) {
        this.campaignHistoryService = campaignHistoryService;
    }

    /**
     * POST  /campaign-histories : Create a new campaignHistory.
     *
     * @param campaignHistoryDTO the campaignHistoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new campaignHistoryDTO, or with status 400 (Bad Request) if the campaignHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/campaign-histories")
    @Timed
    public ResponseEntity<CampaignHistoryDTO> createCampaignHistory(@RequestBody CampaignHistoryDTO campaignHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save CampaignHistory : {}", campaignHistoryDTO);
        if (campaignHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new campaignHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CampaignHistoryDTO result = campaignHistoryService.save(campaignHistoryDTO);
        return ResponseEntity.created(new URI("/api/campaign-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /campaign-histories : Updates an existing campaignHistory.
     *
     * @param campaignHistoryDTO the campaignHistoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated campaignHistoryDTO,
     * or with status 400 (Bad Request) if the campaignHistoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the campaignHistoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/campaign-histories")
    @Timed
    public ResponseEntity<CampaignHistoryDTO> updateCampaignHistory(@RequestBody CampaignHistoryDTO campaignHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update CampaignHistory : {}", campaignHistoryDTO);
        if (campaignHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CampaignHistoryDTO result = campaignHistoryService.save(campaignHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, campaignHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /campaign-histories : get all the campaignHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of campaignHistories in body
     */
    @GetMapping("/campaign-histories")
    @Timed
    public ResponseEntity<List<CampaignHistoryDTO>> getAllCampaignHistories(Pageable pageable) {
        log.debug("REST request to get a page of CampaignHistories");
        Page<CampaignHistoryDTO> page = campaignHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/campaign-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /campaign-histories/:id : get the "id" campaignHistory.
     *
     * @param id the id of the campaignHistoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the campaignHistoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/campaign-histories/{id}")
    @Timed
    public ResponseEntity<CampaignHistoryDTO> getCampaignHistory(@PathVariable Long id) {
        log.debug("REST request to get CampaignHistory : {}", id);
        Optional<CampaignHistoryDTO> campaignHistoryDTO = campaignHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(campaignHistoryDTO);
    }

    /**
     * DELETE  /campaign-histories/:id : delete the "id" campaignHistory.
     *
     * @param id the id of the campaignHistoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/campaign-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteCampaignHistory(@PathVariable Long id) {
        log.debug("REST request to delete CampaignHistory : {}", id);
        campaignHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
