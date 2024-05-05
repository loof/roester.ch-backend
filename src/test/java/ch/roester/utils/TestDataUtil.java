package ch.roester.utils;

import ch.roester.app_user.AppUser;
import ch.roester.event.Event;
import ch.roester.event.EventResponseDTO;
import ch.roester.product.Product;
import ch.roester.product.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestDataUtil {

    public static String JSON_ALL_PRODUCT_DTOS = "{\"totalItems\":4,\"totalPages\":1,\"currentPage\":0,\"products\":[{\"id\":1,\"name\":\"ProductDTO1\",\"description\":\"DescriptionDTO1\",\"eventIds\":null,\"sizePrices\":null,\"tags\":null,\"properties\":null},{\"id\":2,\"name\":\"ProductDTO2\",\"description\":\"DescriptionDTO2\",\"eventIds\":null,\"sizePrices\":null,\"tags\":null,\"properties\":null},{\"id\":3,\"name\":\"ProductDTO3\",\"description\":\"DescriptionDTO3\",\"eventIds\":null,\"sizePrices\":null,\"tags\":null,\"properties\":null},{\"id\":4,\"name\":\"ProductDTO4\",\"description\":\"DescriptionDTO4\",\"eventIds\":null,\"sizePrices\":null,\"tags\":null,\"properties\":null}]}";
    public static String JSON_ALL_EVENTS_DTOS = "{\"totalItems\":4,\"totalPages\":1,\"currentPage\":0,\"events\":[{\"id\":1,\"name\":\"EventDTO1\",\"description\":\"DescriptionDTO1\",\"date\":\"2024-12-24T16:01:00\",\"location\":null,\"productEvents\":null},{\"id\":2,\"name\":\"EventDTO2\",\"description\":\"DescriptionDTO2\",\"date\":\"2024-12-24T16:02:00\",\"location\":null,\"productEvents\":null},{\"id\":3,\"name\":\"EventDTO3\",\"description\":\"DescriptionDTO3\",\"date\":\"2024-12-24T16:03:00\",\"location\":null,\"productEvents\":null},{\"id\":4,\"name\":\"EventDTO4\",\"description\":\"DescriptionDTO4\",\"date\":\"2024-12-24T16:04:00\",\"location\":null,\"productEvents\":null}]}";


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

    public static Event getTestEvent() {
        return getTestEvents().getContent().get(0);
    }

    public static Page<Event> getTestEvents() {
        List<Event> events = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            Event event = new Event();
            event.setId(i);
            event.setName("Event" + i);
            event.setDescription("Description" + i);
            LocalDateTime date = LocalDateTime.of(2024, 12, 24, 16, i, 0);
            event.setDate(date);
            events.add(event);
        }

        return new PageImpl<>(events);
    }

    public static EventResponseDTO getTestEventDTO() {
        return getTestEventsDTO().getContent().get(0);
    }

    public static Page<EventResponseDTO> getTestEventsDTO() {
        List<EventResponseDTO> eventResponseDTOS = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            EventResponseDTO eventResponseDTO = new EventResponseDTO();
            eventResponseDTO.setId(i);
            eventResponseDTO.setName("EventDTO" + i);
            eventResponseDTO.setDescription("DescriptionDTO" + i);
            LocalDateTime date = LocalDateTime.of(2024, 12, 24, 16, i, 0);
            eventResponseDTO.setDate(date);
            eventResponseDTOS.add(eventResponseDTO);
        }

        return new PageImpl<>(eventResponseDTOS);
    }

}
