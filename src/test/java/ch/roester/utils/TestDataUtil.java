package ch.roester.utils;

import ch.roester.app_user.AppUser;
import ch.roester.product.Product;
import ch.roester.product.ProductRequestDTO;

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

    public static Product getTestCoffee() {
        return getTestCoffees().get(0);
    }

    public static List<Product> getTestCoffees() {
        List<Product> coffees = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            Product coffee = new Product();
            coffee.setId(i);
            coffee.setName("Coffee" + i);
            coffees.add(coffee);
        }

        return coffees;
    }

    public static ProductRequestDTO getTestCoffeeDTO() {
        return getTestCoffeesDTO().get(0);
    }

    public static List<ProductRequestDTO> getTestCoffeesDTO() {
        List<ProductRequestDTO> coffees = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            ProductRequestDTO coffee = new ProductRequestDTO();
            coffee.setId(i);
            coffee.setName("CoffeeDTO" + i);
            coffees.add(coffee);
        }

        return coffees;
    }

}
