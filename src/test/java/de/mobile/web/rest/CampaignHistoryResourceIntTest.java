package de.mobile.web.rest;

import de.mobile.CampaignToolApp;

import de.mobile.domain.CampaignHistory;
import de.mobile.repository.CampaignHistoryRepository;
import de.mobile.service.CampaignHistoryService;
import de.mobile.service.dto.CampaignHistoryDTO;
import de.mobile.service.mapper.CampaignHistoryMapper;
import de.mobile.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static de.mobile.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CampaignHistoryResource REST controller.
 *
 * @see CampaignHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CampaignToolApp.class)
public class CampaignHistoryResourceIntTest {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CampaignHistoryRepository campaignHistoryRepository;

    @Autowired
    private CampaignHistoryMapper campaignHistoryMapper;
    
    @Autowired
    private CampaignHistoryService campaignHistoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCampaignHistoryMockMvc;

    private CampaignHistory campaignHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CampaignHistoryResource campaignHistoryResource = new CampaignHistoryResource(campaignHistoryService);
        this.restCampaignHistoryMockMvc = MockMvcBuilders.standaloneSetup(campaignHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CampaignHistory createEntity(EntityManager em) {
        CampaignHistory campaignHistory = new CampaignHistory()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return campaignHistory;
    }

    @Before
    public void initTest() {
        campaignHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createCampaignHistory() throws Exception {
        int databaseSizeBeforeCreate = campaignHistoryRepository.findAll().size();

        // Create the CampaignHistory
        CampaignHistoryDTO campaignHistoryDTO = campaignHistoryMapper.toDto(campaignHistory);
        restCampaignHistoryMockMvc.perform(post("/api/campaign-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the CampaignHistory in the database
        List<CampaignHistory> campaignHistoryList = campaignHistoryRepository.findAll();
        assertThat(campaignHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        CampaignHistory testCampaignHistory = campaignHistoryList.get(campaignHistoryList.size() - 1);
        assertThat(testCampaignHistory.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testCampaignHistory.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createCampaignHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = campaignHistoryRepository.findAll().size();

        // Create the CampaignHistory with an existing ID
        campaignHistory.setId(1L);
        CampaignHistoryDTO campaignHistoryDTO = campaignHistoryMapper.toDto(campaignHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCampaignHistoryMockMvc.perform(post("/api/campaign-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CampaignHistory in the database
        List<CampaignHistory> campaignHistoryList = campaignHistoryRepository.findAll();
        assertThat(campaignHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCampaignHistories() throws Exception {
        // Initialize the database
        campaignHistoryRepository.saveAndFlush(campaignHistory);

        // Get all the campaignHistoryList
        restCampaignHistoryMockMvc.perform(get("/api/campaign-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campaignHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getCampaignHistory() throws Exception {
        // Initialize the database
        campaignHistoryRepository.saveAndFlush(campaignHistory);

        // Get the campaignHistory
        restCampaignHistoryMockMvc.perform(get("/api/campaign-histories/{id}", campaignHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(campaignHistory.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCampaignHistory() throws Exception {
        // Get the campaignHistory
        restCampaignHistoryMockMvc.perform(get("/api/campaign-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCampaignHistory() throws Exception {
        // Initialize the database
        campaignHistoryRepository.saveAndFlush(campaignHistory);

        int databaseSizeBeforeUpdate = campaignHistoryRepository.findAll().size();

        // Update the campaignHistory
        CampaignHistory updatedCampaignHistory = campaignHistoryRepository.findById(campaignHistory.getId()).get();
        // Disconnect from session so that the updates on updatedCampaignHistory are not directly saved in db
        em.detach(updatedCampaignHistory);
        updatedCampaignHistory
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        CampaignHistoryDTO campaignHistoryDTO = campaignHistoryMapper.toDto(updatedCampaignHistory);

        restCampaignHistoryMockMvc.perform(put("/api/campaign-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the CampaignHistory in the database
        List<CampaignHistory> campaignHistoryList = campaignHistoryRepository.findAll();
        assertThat(campaignHistoryList).hasSize(databaseSizeBeforeUpdate);
        CampaignHistory testCampaignHistory = campaignHistoryList.get(campaignHistoryList.size() - 1);
        assertThat(testCampaignHistory.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testCampaignHistory.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCampaignHistory() throws Exception {
        int databaseSizeBeforeUpdate = campaignHistoryRepository.findAll().size();

        // Create the CampaignHistory
        CampaignHistoryDTO campaignHistoryDTO = campaignHistoryMapper.toDto(campaignHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampaignHistoryMockMvc.perform(put("/api/campaign-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CampaignHistory in the database
        List<CampaignHistory> campaignHistoryList = campaignHistoryRepository.findAll();
        assertThat(campaignHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCampaignHistory() throws Exception {
        // Initialize the database
        campaignHistoryRepository.saveAndFlush(campaignHistory);

        int databaseSizeBeforeDelete = campaignHistoryRepository.findAll().size();

        // Get the campaignHistory
        restCampaignHistoryMockMvc.perform(delete("/api/campaign-histories/{id}", campaignHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CampaignHistory> campaignHistoryList = campaignHistoryRepository.findAll();
        assertThat(campaignHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampaignHistory.class);
        CampaignHistory campaignHistory1 = new CampaignHistory();
        campaignHistory1.setId(1L);
        CampaignHistory campaignHistory2 = new CampaignHistory();
        campaignHistory2.setId(campaignHistory1.getId());
        assertThat(campaignHistory1).isEqualTo(campaignHistory2);
        campaignHistory2.setId(2L);
        assertThat(campaignHistory1).isNotEqualTo(campaignHistory2);
        campaignHistory1.setId(null);
        assertThat(campaignHistory1).isNotEqualTo(campaignHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampaignHistoryDTO.class);
        CampaignHistoryDTO campaignHistoryDTO1 = new CampaignHistoryDTO();
        campaignHistoryDTO1.setId(1L);
        CampaignHistoryDTO campaignHistoryDTO2 = new CampaignHistoryDTO();
        assertThat(campaignHistoryDTO1).isNotEqualTo(campaignHistoryDTO2);
        campaignHistoryDTO2.setId(campaignHistoryDTO1.getId());
        assertThat(campaignHistoryDTO1).isEqualTo(campaignHistoryDTO2);
        campaignHistoryDTO2.setId(2L);
        assertThat(campaignHistoryDTO1).isNotEqualTo(campaignHistoryDTO2);
        campaignHistoryDTO1.setId(null);
        assertThat(campaignHistoryDTO1).isNotEqualTo(campaignHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(campaignHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(campaignHistoryMapper.fromId(null)).isNull();
    }
}
