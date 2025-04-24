package com.flood_web.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class Alert {

    private Long id;

    private String alertType;

    private String message;

    private LocalDateTime date;

    private String nameNotifiedPerson;

    private String riskLevel;

}