package com.flood_web.controller;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "person_risk_log_entity")
public class PersonRiskLog {

    private String id;
    private String personName;
    private String phoneNumber;
    private String riskLevel;
    private String zoneName;
    private LocalDateTime date; // fecha original

    private String dateFormatted; // nueva propiedad para la fecha formateada

    // getters y setters de todos los campos...

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDateFormatted() {
        return dateFormatted;
    }

    public void setDateFormatted(String dateFormatted) {
        this.dateFormatted = dateFormatted;
    }

    // resto getters y setters...
}
