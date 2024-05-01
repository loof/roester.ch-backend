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
class ProductControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService coffeeService;

    @MockBean
    private ProductMapper productMapper;


    @Test
    public void checkPost_whenValidNewProduct_thenIsCreated() throws Exception {

        // 1. Mockito Mocking
        Mockito.when(coffeeService.save(any(Product.class))).thenReturn(TestDataUtil.getTestProduct());
        Mockito.when(productMapper.toResponseDTO(any(Product.class))).thenReturn(TestDataUtil.getTestProductDTO());
        Mockito.when(productMapper.fromRequestDTO(any(ProductRequestDTO.class))).thenReturn(TestDataUtil.getTestProduct());

        mockMvc.perform(post(ProductController.REQUEST_MAPPING)
                        .contentType("application/json")
                        .content("{\"name\":\"CoffeeDTO1\", \"appUserId\":\"1\"}"))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("CoffeeDTO1")));
    }

    @Test
    public void checkPost_whenInvalidICoffee_thenIsBadRequest() throws Exception {

        Mockito.when(coffeeService.save(any(Product.class))).thenThrow(DataIntegrityViolationException.class);
        Mockito.when(productMapper.toResponseDTO(any(Product.class))).thenReturn(TestDataUtil.getTestProductDTO());
        Mockito.when(productMapper.fromRequestDTO(any(ProductRequestDTO.class))).thenReturn(TestDataUtil.getTestProduct());

        mockMvc.perform(post(ProductController.REQUEST_MAPPING)
                        .contentType("application/json")
                        .content("{\"wrongFieldName\":\"Product\"}"))

                .andExpect(status().isBadRequest());
    }


}