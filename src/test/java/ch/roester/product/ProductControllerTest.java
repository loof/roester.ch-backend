package ch.roester.product;

import ch.roester.utils.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
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
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;


    @Test
    public void checkPost_whenValidNewProduct_thenIsCreated() throws Exception {
        Mockito.when(productService.save(any(ProductRequestDTO.class))).thenReturn(TestDataUtil.getTestProductDTO());
        Mockito.when(productMapper.toResponseDTO(any(Product.class))).thenReturn(TestDataUtil.getTestProductDTO());
        Mockito.when(productMapper.fromRequestDTO(any(ProductRequestDTO.class))).thenReturn(TestDataUtil.getTestProduct());

        mockMvc.perform(post(ProductController.REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"ProductDTO1\", \"appUserId\":\"1\"}"))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("ProductDTO1")));
    }

    @Test
    public void checkPost_whenInvalidIProduct_thenIsBadRequest() throws Exception {
        Mockito.when(productService.save(any(ProductRequestDTO.class))).thenThrow(DataIntegrityViolationException.class);
        Mockito.when(productMapper.toResponseDTO(any(Product.class))).thenReturn(TestDataUtil.getTestProductDTO());
        Mockito.when(productMapper.fromRequestDTO(any(ProductRequestDTO.class))).thenReturn(TestDataUtil.getTestProduct());

        mockMvc.perform(post(ProductController.REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"wrongFieldName\":\"Product\"}"))

                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkGetAll_whenWithoutParams_thenAllAreReturned() throws Exception {
        //Mockito.when(productService.findAll())
    }



}