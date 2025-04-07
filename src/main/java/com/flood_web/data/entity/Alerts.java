package com.flood_web.data.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
public class Alerts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    private LocalDateTime fecha;

    public Alerts() {
        this.fecha = LocalDateTime.now(); // fecha automática
    }

    public Alerts(String mensaje) {
        this.mensaje = mensaje;
        this.fecha = LocalDateTime.now();
    }


}