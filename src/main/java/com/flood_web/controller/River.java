package com.flood_web.controller;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record River(String id,
                    String name) {
}
