package ch.roester.event;

import ch.roester.utils.TestDataUtil;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = EventController.class)
@AutoConfigureMockMvc(addFilters = false)
class EventControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private EventMapper eventMapper;


    @Test
    public void checkPost_whenValidNewEvent_thenIsCreated() throws Exception {
        Mockito.when(eventService.save(any(EventRequestDTO.class))).thenReturn(TestDataUtil.getTestEventDTO());
        Mockito.when(eventMapper.toResponseDTO(any(Event.class))).thenReturn(TestDataUtil.getTestEventDTO());
        Mockito.when(eventMapper.fromRequestDTO(any(EventRequestDTO.class))).thenReturn(TestDataUtil.getTestEvent());

        mockMvc.perform(post(EventController.REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"date\":\"2024-12-24T16:01:00\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.date", is("2024-12-24T16:01:00")));
    }

    @Test
    public void checkPost_whenInvalidIEvent_thenIsBadRequest() throws Exception {
        Mockito.when(eventService.save(any(EventRequestDTO.class))).thenThrow(DataIntegrityViolationException.class);
        Mockito.when(eventMapper.toResponseDTO(any(Event.class))).thenReturn(TestDataUtil.getTestEventDTO());
        Mockito.when(eventMapper.fromRequestDTO(any(EventRequestDTO.class))).thenReturn(TestDataUtil.getTestEvent());

        mockMvc.perform(post(EventController.REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"wrongFieldName\":\"Event\"}"))

                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkGetAll_whenWithoutParams_thenAllAreReturned() throws Exception {
        Mockito.when(eventService.findAll(any(Pageable.class))).thenReturn(TestDataUtil.getTestEventsDTO());

        mockMvc.perform(get(EventController.REQUEST_MAPPING))
                .andExpect(status().isOk())
                .andExpect(content().json(TestDataUtil.JSON_ALL_EVENTS_DTOS));

    }

    @Test
    public void checkFindById_whenInvalidId_thenIsNotFound() throws Exception {
        Mockito.when(eventService.findById(eq(0))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get(EventController.REQUEST_MAPPING + "/" + 0))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkFindById_whenValidId_thenEventIsReturned() throws Exception {
        EventResponseDTO expected = TestDataUtil.getTestEventDTO();
        Mockito.when(eventService.findById(eq(1))).thenReturn(expected);

        mockMvc.perform(get(EventController.REQUEST_MAPPING + "/" + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("EventDTO1")));
    }

    @Test
    public void checkGetAll_whenSearchQueryGiven_thenFilteredEventsAreReturned() throws Exception {
        Mockito.when(eventService.findBySearchQuery(eq("Event"), any(Pageable.class))).thenReturn(TestDataUtil.getTestEventsDTO());

        mockMvc.perform(get(EventController.REQUEST_MAPPING + "?searchQuery=Event"))
                .andExpect(status().isOk())
                .andExpect(content().json(TestDataUtil.JSON_ALL_EVENTS_DTOS));

    }

    @Test
    public void checkDelete_whenValidId_thenIsNoContent() throws Exception {
        mockMvc.perform(delete(EventController.REQUEST_MAPPING + "/" + 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void checkDelete_whenInvalidId_thenIsNotFound() throws Exception {
        Mockito.doThrow(EmptyResultDataAccessException.class).when(eventService).deleteById(0);

        mockMvc.perform(delete(EventController.REQUEST_MAPPING + "/" + 0))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkPatch_whenValidEvent_thenIsOk() throws Exception {
        LocalDateTime newDate = LocalDateTime.of(2024, 12, 24, 16, 20, 0);

        EventResponseDTO expected = TestDataUtil.getTestEventDTO();
        expected.setDate(newDate);

        Mockito.when(eventService.update(eq(1), any(EventRequestDTO.class))).thenReturn(expected);

        mockMvc.perform(patch(EventController.REQUEST_MAPPING + "/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"date\":\"2024-12-24T16:20:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date", is("2024-12-24T16:20:00")));

    }

    @Test
    public void checkPatch_whenInvalidEvent_thenIsBadRequest() throws Exception {
        String newName = "";

        EventResponseDTO expected = TestDataUtil.getTestEventDTO();
        expected.setName(newName);

        mockMvc.perform(patch(EventController.REQUEST_MAPPING + "/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());

    }



}