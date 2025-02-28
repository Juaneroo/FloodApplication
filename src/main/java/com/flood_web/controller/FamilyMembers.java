package com.flood_web.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class FamilyMembers{

    private String name;

    private String idNumber;

    private String telephone;

    private boolean active;

    private Family family;
}