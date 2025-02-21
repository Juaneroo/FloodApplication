package com.flood_web.data.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Entity
@Table(name = "family_members")
public class FamilyMembersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String idNumber;

    @Column
    private String name;

    @Column
    private String telephone;

    @Column
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private FamilyEntity family;





    /*@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "family_zone", joinColumns = @JoinColumn(name = "family_id"),
            inverseJoinColumns = @JoinColumn(name = "zone_id"))
    private List<ZoneEntity> zones;*/
}