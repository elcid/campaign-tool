package de.mobile.web.rest;

import de.mobile.CampaignToolApp;

import de.mobile.domain.Personalisation;
import de.mobile.repository.PersonalisationRepository;
import de.mobile.service.PersonalisationService;
import de.mobile.service.dto.PersonalisationDTO;
import de.mobile.service.mapper.PersonalisationMapper;
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
import java.util.List;


import static de.mobile.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.mobile.domain.enumeration.AccountType;
/**
 * Test class for the PersonalisationResource REST controller.
 *
 * @see PersonalisationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CampaignToolApp.class)
public class PersonalisationResourceIntTest {

    private static final Long DEFAULT_CUSTOMER_NUMBER = 1L;
    private static final Long UPDATED_CUSTOMER_NUMBER = 2L;

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final AccountType DEFAULT_ACCOUNT_TYPE = AccountType.COMPACT;
    private static final AccountType UPDATED_ACCOUNT_TYPE = AccountType.COMFORT;

    @Autowired
    private PersonalisationRepository personalisationRepository;

    @Autowired
    private PersonalisationMapper personalisationMapper;
    
    @Autowired
    private PersonalisationService personalisationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonalisationMockMvc;

    private Personalisation personalisation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonalisationResource personalisationResource = new PersonalisationResource(personalisationService);
        this.restPersonalisationMockMvc = MockMvcBuilders.standaloneSetup(personalisationResource)
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
    public static Personalisation createEntity(EntityManager em) {
        Personalisation personalisation = new Personalisation()
            .customerNumber(DEFAULT_CUSTOMER_NUMBER)
            .userId(DEFAULT_USER_ID)
            .accountType(DEFAULT_ACCOUNT_TYPE);
        return personalisation;
    }

    @Before
    public void initTest() {
        personalisation = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonalisation() throws Exception {
        int databaseSizeBeforeCreate = personalisationRepository.findAll().size();

        // Create the Personalisation
        PersonalisationDTO personalisationDTO = personalisationMapper.toDto(personalisation);
        restPersonalisationMockMvc.perform(post("/api/personalisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalisationDTO)))
            .andExpect(status().isCreated());

        // Validate the Personalisation in the database
        List<Personalisation> personalisationList = personalisationRepository.findAll();
        assertThat(personalisationList).hasSize(databaseSizeBeforeCreate + 1);
        Personalisation testPersonalisation = personalisationList.get(personalisationList.size() - 1);
        assertThat(testPersonalisation.getCustomerNumber()).isEqualTo(DEFAULT_CUSTOMER_NUMBER);
        assertThat(testPersonalisation.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPersonalisation.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void createPersonalisationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personalisationRepository.findAll().size();

        // Create the Personalisation with an existing ID
        personalisation.setId(1L);
        PersonalisationDTO personalisationDTO = personalisationMapper.toDto(personalisation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonalisationMockMvc.perform(post("/api/personalisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalisationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Personalisation in the database
        List<Personalisation> personalisationList = personalisationRepository.findAll();
        assertThat(personalisationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonalisations() throws Exception {
        // Initialize the database
        personalisationRepository.saveAndFlush(personalisation);

        // Get all the personalisationList
        restPersonalisationMockMvc.perform(get("/api/personalisations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personalisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerNumber").value(hasItem(DEFAULT_CUSTOMER_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getPersonalisation() throws Exception {
        // Initialize the database
        personalisationRepository.saveAndFlush(personalisation);

        // Get the personalisation
        restPersonalisationMockMvc.perform(get("/api/personalisations/{id}", personalisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personalisation.getId().intValue()))
            .andExpect(jsonPath("$.customerNumber").value(DEFAULT_CUSTOMER_NUMBER.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonalisation() throws Exception {
        // Get the personalisation
        restPersonalisationMockMvc.perform(get("/api/personalisations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonalisation() throws Exception {
        // Initialize the database
        personalisationRepository.saveAndFlush(personalisation);

        int databaseSizeBeforeUpdate = personalisationRepository.findAll().size();

        // Update the personalisation
        Personalisation updatedPersonalisation = personalisationRepository.findById(personalisation.getId()).get();
        // Disconnect from session so that the updates on updatedPersonalisation are not directly saved in db
        em.detach(updatedPersonalisation);
        updatedPersonalisation
            .customerNumber(UPDATED_CUSTOMER_NUMBER)
            .userId(UPDATED_USER_ID)
            .accountType(UPDATED_ACCOUNT_TYPE);
        PersonalisationDTO personalisationDTO = personalisationMapper.toDto(updatedPersonalisation);

        restPersonalisationMockMvc.perform(put("/api/personalisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalisationDTO)))
            .andExpect(status().isOk());

        // Validate the Personalisation in the database
        List<Personalisation> personalisationList = personalisationRepository.findAll();
        assertThat(personalisationList).hasSize(databaseSizeBeforeUpdate);
        Personalisation testPersonalisation = personalisationList.get(personalisationList.size() - 1);
        assertThat(testPersonalisation.getCustomerNumber()).isEqualTo(UPDATED_CUSTOMER_NUMBER);
        assertThat(testPersonalisation.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPersonalisation.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonalisation() throws Exception {
        int databaseSizeBeforeUpdate = personalisationRepository.findAll().size();

        // Create the Personalisation
        PersonalisationDTO personalisationDTO = personalisationMapper.toDto(personalisation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonalisationMockMvc.perform(put("/api/personalisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalisationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Personalisation in the database
        List<Personalisation> personalisationList = personalisationRepository.findAll();
        assertThat(personalisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonalisation() throws Exception {
        // Initialize the database
        personalisationRepository.saveAndFlush(personalisation);

        int databaseSizeBeforeDelete = personalisationRepository.findAll().size();

        // Get the personalisation
        restPersonalisationMockMvc.perform(delete("/api/personalisations/{id}", personalisation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Personalisation> personalisationList = personalisationRepository.findAll();
        assertThat(personalisationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Personalisation.class);
        Personalisation personalisation1 = new Personalisation();
        personalisation1.setId(1L);
        Personalisation personalisation2 = new Personalisation();
        personalisation2.setId(personalisation1.getId());
        assertThat(personalisation1).isEqualTo(personalisation2);
        personalisation2.setId(2L);
        assertThat(personalisation1).isNotEqualTo(personalisation2);
        personalisation1.setId(null);
        assertThat(personalisation1).isNotEqualTo(personalisation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonalisationDTO.class);
        PersonalisationDTO personalisationDTO1 = new PersonalisationDTO();
        personalisationDTO1.setId(1L);
        PersonalisationDTO personalisationDTO2 = new PersonalisationDTO();
        assertThat(personalisationDTO1).isNotEqualTo(personalisationDTO2);
        personalisationDTO2.setId(personalisationDTO1.getId());
        assertThat(personalisationDTO1).isEqualTo(personalisationDTO2);
        personalisationDTO2.setId(2L);
        assertThat(personalisationDTO1).isNotEqualTo(personalisationDTO2);
        personalisationDTO1.setId(null);
        assertThat(personalisationDTO1).isNotEqualTo(personalisationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(personalisationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(personalisationMapper.fromId(null)).isNull();
    }
}
