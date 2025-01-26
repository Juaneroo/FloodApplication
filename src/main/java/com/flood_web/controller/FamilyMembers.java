package com.flood_web.controller;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record FamilyMembers(String id,
                     String name,

                     String idNumber,

                     String telephone,

                     Boolean active) {
}