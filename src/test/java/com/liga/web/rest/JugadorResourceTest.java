package com.liga.web.rest;

import com.liga.Application;
import com.liga.domain.Jugador;
import com.liga.repository.JugadorRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the JugadorResource REST controller.
 *
 * @see JugadorResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JugadorResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_NOMBRE = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE = "UPDATED_TEXT";

    private static final DateTime DEFAULT_NACIMIENTO = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_NACIMIENTO = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_NACIMIENTO_STR = dateTimeFormatter.print(DEFAULT_NACIMIENTO);

    private static final Integer DEFAULT_CANASTAS = 0;
    private static final Integer UPDATED_CANASTAS = 1;

    private static final Integer DEFAULT_ASISTENCIAS = 0;
    private static final Integer UPDATED_ASISTENCIAS = 1;

    private static final Integer DEFAULT_REBOTES = 0;
    private static final Integer UPDATED_REBOTES = 1;
    private static final String DEFAULT_POSICION = "SAMPLE_TEXT";
    private static final String UPDATED_POSICION = "UPDATED_TEXT";

    @Inject
    private JugadorRepository jugadorRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restJugadorMockMvc;

    private Jugador jugador;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JugadorResource jugadorResource = new JugadorResource();
        ReflectionTestUtils.setField(jugadorResource, "jugadorRepository", jugadorRepository);
        this.restJugadorMockMvc = MockMvcBuilders.standaloneSetup(jugadorResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jugador = new Jugador();
        jugador.setNombre(DEFAULT_NOMBRE);
        jugador.setNacimiento(DEFAULT_NACIMIENTO);
        jugador.setCanastas(DEFAULT_CANASTAS);
        jugador.setAsistencias(DEFAULT_ASISTENCIAS);
        jugador.setRebotes(DEFAULT_REBOTES);
        jugador.setPosicion(DEFAULT_POSICION);
    }

    @Test
    @Transactional
    public void createJugador() throws Exception {
        int databaseSizeBeforeCreate = jugadorRepository.findAll().size();

        // Create the Jugador

        restJugadorMockMvc.perform(post("/api/jugadors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jugador)))
                .andExpect(status().isCreated());

        // Validate the Jugador in the database
        List<Jugador> jugadors = jugadorRepository.findAll();
        assertThat(jugadors).hasSize(databaseSizeBeforeCreate + 1);
        Jugador testJugador = jugadors.get(jugadors.size() - 1);
        assertThat(testJugador.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testJugador.getNacimiento().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_NACIMIENTO);
        assertThat(testJugador.getCanastas()).isEqualTo(DEFAULT_CANASTAS);
        assertThat(testJugador.getAsistencias()).isEqualTo(DEFAULT_ASISTENCIAS);
        assertThat(testJugador.getRebotes()).isEqualTo(DEFAULT_REBOTES);
        assertThat(testJugador.getPosicion()).isEqualTo(DEFAULT_POSICION);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = jugadorRepository.findAll().size();
        // set the field null
        jugador.setNombre(null);

        // Create the Jugador, which fails.

        restJugadorMockMvc.perform(post("/api/jugadors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jugador)))
                .andExpect(status().isBadRequest());

        List<Jugador> jugadors = jugadorRepository.findAll();
        assertThat(jugadors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNacimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = jugadorRepository.findAll().size();
        // set the field null
        jugador.setNacimiento(null);

        // Create the Jugador, which fails.

        restJugadorMockMvc.perform(post("/api/jugadors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jugador)))
                .andExpect(status().isBadRequest());

        List<Jugador> jugadors = jugadorRepository.findAll();
        assertThat(jugadors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCanastasIsRequired() throws Exception {
        int databaseSizeBeforeTest = jugadorRepository.findAll().size();
        // set the field null
        jugador.setCanastas(null);

        // Create the Jugador, which fails.

        restJugadorMockMvc.perform(post("/api/jugadors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jugador)))
                .andExpect(status().isBadRequest());

        List<Jugador> jugadors = jugadorRepository.findAll();
        assertThat(jugadors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAsistenciasIsRequired() throws Exception {
        int databaseSizeBeforeTest = jugadorRepository.findAll().size();
        // set the field null
        jugador.setAsistencias(null);

        // Create the Jugador, which fails.

        restJugadorMockMvc.perform(post("/api/jugadors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jugador)))
                .andExpect(status().isBadRequest());

        List<Jugador> jugadors = jugadorRepository.findAll();
        assertThat(jugadors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRebotesIsRequired() throws Exception {
        int databaseSizeBeforeTest = jugadorRepository.findAll().size();
        // set the field null
        jugador.setRebotes(null);

        // Create the Jugador, which fails.

        restJugadorMockMvc.perform(post("/api/jugadors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jugador)))
                .andExpect(status().isBadRequest());

        List<Jugador> jugadors = jugadorRepository.findAll();
        assertThat(jugadors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPosicionIsRequired() throws Exception {
        int databaseSizeBeforeTest = jugadorRepository.findAll().size();
        // set the field null
        jugador.setPosicion(null);

        // Create the Jugador, which fails.

        restJugadorMockMvc.perform(post("/api/jugadors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jugador)))
                .andExpect(status().isBadRequest());

        List<Jugador> jugadors = jugadorRepository.findAll();
        assertThat(jugadors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJugadors() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);

        // Get all the jugadors
        restJugadorMockMvc.perform(get("/api/jugadors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jugador.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].nacimiento").value(hasItem(DEFAULT_NACIMIENTO_STR)))
                .andExpect(jsonPath("$.[*].canastas").value(hasItem(DEFAULT_CANASTAS)))
                .andExpect(jsonPath("$.[*].asistencias").value(hasItem(DEFAULT_ASISTENCIAS)))
                .andExpect(jsonPath("$.[*].rebotes").value(hasItem(DEFAULT_REBOTES)))
                .andExpect(jsonPath("$.[*].posicion").value(hasItem(DEFAULT_POSICION.toString())));
    }

    @Test
    @Transactional
    public void getJugador() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);

        // Get the jugador
        restJugadorMockMvc.perform(get("/api/jugadors/{id}", jugador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jugador.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.nacimiento").value(DEFAULT_NACIMIENTO_STR))
            .andExpect(jsonPath("$.canastas").value(DEFAULT_CANASTAS))
            .andExpect(jsonPath("$.asistencias").value(DEFAULT_ASISTENCIAS))
            .andExpect(jsonPath("$.rebotes").value(DEFAULT_REBOTES))
            .andExpect(jsonPath("$.posicion").value(DEFAULT_POSICION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJugador() throws Exception {
        // Get the jugador
        restJugadorMockMvc.perform(get("/api/jugadors/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJugador() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);

		int databaseSizeBeforeUpdate = jugadorRepository.findAll().size();

        // Update the jugador
        jugador.setNombre(UPDATED_NOMBRE);
        jugador.setNacimiento(UPDATED_NACIMIENTO);
        jugador.setCanastas(UPDATED_CANASTAS);
        jugador.setAsistencias(UPDATED_ASISTENCIAS);
        jugador.setRebotes(UPDATED_REBOTES);
        jugador.setPosicion(UPDATED_POSICION);
        

        restJugadorMockMvc.perform(put("/api/jugadors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jugador)))
                .andExpect(status().isOk());

        // Validate the Jugador in the database
        List<Jugador> jugadors = jugadorRepository.findAll();
        assertThat(jugadors).hasSize(databaseSizeBeforeUpdate);
        Jugador testJugador = jugadors.get(jugadors.size() - 1);
        assertThat(testJugador.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testJugador.getNacimiento().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_NACIMIENTO);
        assertThat(testJugador.getCanastas()).isEqualTo(UPDATED_CANASTAS);
        assertThat(testJugador.getAsistencias()).isEqualTo(UPDATED_ASISTENCIAS);
        assertThat(testJugador.getRebotes()).isEqualTo(UPDATED_REBOTES);
        assertThat(testJugador.getPosicion()).isEqualTo(UPDATED_POSICION);
    }

    @Test
    @Transactional
    public void deleteJugador() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);

		int databaseSizeBeforeDelete = jugadorRepository.findAll().size();

        // Get the jugador
        restJugadorMockMvc.perform(delete("/api/jugadors/{id}", jugador.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Jugador> jugadors = jugadorRepository.findAll();
        assertThat(jugadors).hasSize(databaseSizeBeforeDelete - 1);
    }
}
