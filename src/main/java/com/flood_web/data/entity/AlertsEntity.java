package com.flood_web.data.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")  // Opcional: asegura que se mapea a la tabla "alerts"
public class AlertsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    private LocalDateTime fecha;

    public AlertsEntity() {
        this.fecha = LocalDateTime.now(); // fecha automática
    }

    public AlertsEntity(String mensaje) {
        this.mensaje = mensaje;
        this.fecha = LocalDateTime.now();
    }

    // Getters y setters...
}