package ch.roester.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class OrderBuilder {
    public static List<String> getIds() {
        return Collections.singletonList("1");
    }

    public static OrderDto getDto() {
        OrderDto dto = new OrderDto();
        dto.setId("1");
        return dto;
    }
}