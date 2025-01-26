package com.flood_web.controller;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record Zone(String id,
                    String name,
                    Boolean active) {
}
