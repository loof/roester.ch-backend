package ch.roester.utils;

import ch.roester.app_user.AppUser;
import ch.roester.product.Product;
import ch.roester.product.ProductRequestDTO;
import ch.roester.product.ProductResponseDTO;
import ch.roester.property.Property;
import ch.roester.property.PropertyResponseDTO;
import ch.roester.tag.Tag;
import ch.roester.tag.TagResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestDataUtil {

    public static String JSON_ALL_PRODUCTS_DTOS = "{\"totalItems\":4,\"totalPages\":1,\"currentPage\":0,\"products\":[{\"id\":1,\"name\":\"ProductDTO1\",\"description\":\"DescriptionDTO1\",\"eventIds\":null,\"sizePriceIds\":null,\"tags\":null,\"properties\":null,\"productOrderIds\":null},{\"id\":2,\"name\":\"ProductDTO2\",\"description\":\"DescriptionDTO2\",\"eventIds\":null,\"sizePriceIds\":null,\"tags\":null,\"properties\":null,\"productOrderIds\":null},{\"id\":3,\"name\":\"ProductDTO3\",\"description\":\"DescriptionDTO3\",\"eventIds\":null,\"sizePriceIds\":null,\"tags\":null,\"properties\":null,\"productOrderIds\":null},{\"id\":4,\"name\":\"ProductDTO4\",\"description\":\"DescriptionDTO4\",\"eventIds\":null,\"sizePriceIds\":null,\"tags\":null,\"properties\":null,\"productOrderIds\":null}]}";

    public static AppUser getTestAppUser() {
        return getTestAppUsers().getContent().get(0);
    }

    public static AppUser getInvalidTestUser() {
        AppUser user = new AppUser();
        user.setId(0);
        return user;
    }

    public static Page<AppUser> getTestAppUsers() {
        List<AppUser> users = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            AppUser user = new AppUser();
            user.setId(i);
            user.setEmail("Email" + i);
            user.setPassword("Password" + i);
            users.add(user);
        }

        return new PageImpl<>(users);
    }

    public static Product getTestProduct() {
        return getTestProducts().getContent().get(0);
    }

    public static Page<Product> getTestProducts() {
        List<Product> products = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            Product product = new Product();
            product.setId(i);
            product.setName("Product" + i);
            product.setDescription("Description" + i);
            products.add(product);
        }

        return new PageImpl<>(products);
    }

    public static ProductResponseDTO getTestProductDTO() {
        return getTestProductsDTO().getContent().get(0);
    }

    public static Page<ProductResponseDTO> getTestProductsDTO() {
        List<ProductResponseDTO> productResponseDTOs = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            productResponseDTO.setId(i);
            productResponseDTO.setName("ProductDTO" + i);
            productResponseDTO.setDescription("DescriptionDTO" + i);
            productResponseDTOs.add(productResponseDTO);
        }

        return new PageImpl<>(productResponseDTOs);
    }

}
