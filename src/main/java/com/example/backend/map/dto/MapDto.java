package com.example.backend.map.dto;

public record MapDto(
        double latitude,
        double longitude
) {
    public static MapDto of(double latitude, double longitude) {
        return new MapDto(latitude, longitude);
    }
}
