package com.flood_web.controller;

import jakarta.annotation.Nonnull;
import lombok.Builder;

import java.util.UUID;

@Builder(setterPrefix = "with")
public record Sensor(String id,
                     String name,
                     Boolean active) {
}
