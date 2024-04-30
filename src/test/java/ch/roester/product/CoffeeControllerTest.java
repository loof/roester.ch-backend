package ch.roester.product;

import ch.roester.utils.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class CoffeeControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService coffeeService;

    @MockBean
    private ProductMapper coffeeMapper;


    @Test
    public void checkPost_whenValidNewCoffee_thenIsCreated() throws Exception {

        // 1. Mockito Mocking
        Mockito.when(coffeeService.save(any(Product.class))).thenReturn(TestDataUtil.getTestCoffee());
        Mockito.when(coffeeMapper.toDto(any(Product.class))).thenReturn(TestDataUtil.getTestCoffeeDTO());
        Mockito.when(coffeeMapper.toEntity(any(ProductRequestDTO.class))).thenReturn(TestDataUtil.getTestCoffee());

        mockMvc.perform(post(ProductController.REQUEST_MAPPING)
                        .contentType("application/json")
                        .content("{\"name\":\"CoffeeDTO1\", \"appUserId\":\"1\"}"))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("CoffeeDTO1")));
    }

    @Test
    public void checkPost_whenInvalidICoffee_thenIsBadRequest() throws Exception {

        Mockito.when(coffeeService.save(any(Product.class))).thenThrow(DataIntegrityViolationException.class);
        Mockito.when(coffeeMapper.toDto(any(Product.class))).thenReturn(TestDataUtil.getTestCoffeeDTO());
        Mockito.when(coffeeMapper.toEntity(any(ProductRequestDTO.class))).thenReturn(TestDataUtil.getTestCoffee());

        mockMvc.perform(post(ProductController.REQUEST_MAPPING)
                        .contentType("application/json")
                        .content("{\"wrongFieldName\":\"Coffee1\"}"))

                .andExpect(status().isBadRequest());
    }


}