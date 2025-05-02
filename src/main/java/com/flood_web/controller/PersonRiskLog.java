package com.flood_web.controller;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "person_risk_log_entity")
public class PersonRiskLog {

    private String personName;

    private String phoneNumber;

    private String riskLevel;

    private String zoneName;

    private String date;
}
