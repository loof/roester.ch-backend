package ch.roester.utils;

import ch.roester.app_user.AppUser;
import ch.roester.product.Product;
import ch.roester.product.ProductRequestDTO;
import ch.roester.product.ProductResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class TestDataUtil {

    public static AppUser getTestAppUser() {
        return getTestAppUsers().get(0);
    }

    public static AppUser getInvalidTestUser() {
        AppUser user = new AppUser();
        user.setId(0);
        return user;
    }

    public static List<AppUser> getTestAppUsers() {
        List<AppUser> users = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            AppUser user = new AppUser();
            user.setId(i);
            user.setEmail("Email" + i);
            user.setPassword("Password" + i);
            users.add(user);
        }

        return users;
    }

    public static Product getTestProduct() {
        return getTestProducts().get(0);
    }

    public static List<Product> getTestProducts() {
        List<Product> coffees = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            Product coffee = new Product();
            coffee.setId(i);
            coffee.setName("Coffee" + i);
            coffees.add(coffee);
        }

        return coffees;
    }

    public static ProductResponseDTO getTestProductDTO() {
        return getTestProductsDTO().get(0);
    }

    public static List<ProductResponseDTO> getTestProductsDTO() {
        List<ProductResponseDTO> productResponseDTOs = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            productResponseDTO.setId(i);
            productResponseDTO.setName("CoffeeDTO" + i);
            productResponseDTOs.add(productResponseDTO);
        }

        return productResponseDTOs;
    }

}
