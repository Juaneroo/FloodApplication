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
public class Administrators{

    private String id;

    private String cedula;

    private String name;

    private String telephone;

    private String entity;

    private boolean active;


}