package com.flood_web.data.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Entity
@Table(name = "administrators")
public class AdministratorsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cedula;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String entity;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String password;

    @Column
    private boolean active;


}