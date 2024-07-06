package ch.roester.controller;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import ch.roester.controller.CustomUtils;
import ch.roester.controller.OrderController;
import ch.roester.dto.OrderDto;
import ch.roester.mapper.EntityMapper;
import ch.roester.mapper.OrderMapper;
import ch.roester.order.Order;
import ch.roester.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

@Transactional
public class OrderControllerTest {
    private static final String ENDPOINT_URL = "/api/order";
    @InjectMocks
    private OrderController orderController;
    @Mock
    private OrderService orderService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(orderController)
                //.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                //.addFilter(CustomFilter::doFilter)
                .build();
    }

    @Test
    public void findAllByPage() throws Exception {
        Page<OrderDto> page = new PageImpl<>(Collections.singletonList(OrderBuilder.getDto()));

        Mockito.when(orderService.findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(page);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", Matchers.hasSize(1)));

        Mockito.verify(orderService, Mockito.times(1)).findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.verifyNoMoreInteractions(orderService);

    }

    @Test
    public void getById() throws Exception {
        Mockito.when(orderService.findById(ArgumentMatchers.anyInteger())).thenReturn(OrderBuilder.getDto());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
        Mockito.verify(orderService, Mockito.times(1)).findById("1");
        Mockito.verifyNoMoreInteractions(orderService);
    }

    @Test
    public void save() throws Exception {
        Mockito.when(orderService.save(ArgumentMatchers.any(OrderDto.class))).thenReturn(OrderBuilder.getDto());

        mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(OrderBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(orderService, Mockito.times(1)).save(ArgumentMatchers.any(OrderDto.class));
        Mockito.verifyNoMoreInteractions(orderService);
    }

    @Test
    public void update() throws Exception {
        Mockito.when(orderService.update(ArgumentMatchers.any(), ArgumentMatchers.anyInteger())).thenReturn(OrderBuilder.getDto());

        mockMvc.perform(
                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(OrderBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(orderService, Mockito.times(1)).update(ArgumentMatchers.any(OrderDto.class), ArgumentMatchers.anyInteger());
        Mockito.verifyNoMoreInteractions(orderService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(orderService).deleteById(ArgumentMatchers.anyInteger());
        mockMvc.perform(
                MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomUtils.asJsonString(OrderBuilder.getIds()))).andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(orderService, Mockito.times(1)).deleteById(Mockito.anyInteger());
        Mockito.verifyNoMoreInteractions(orderService);
    }
}