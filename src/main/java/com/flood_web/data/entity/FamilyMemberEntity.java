package com.flood_web.data.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Entity
@Table(name = "family_member")
public class FamilyMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cedula;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String telephone;

    @Column
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "family_id", nullable = false)
    private FamilyEntity family;
}