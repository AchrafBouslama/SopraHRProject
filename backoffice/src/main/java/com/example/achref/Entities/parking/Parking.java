package com.example.achref.Entities.parking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import com.example.achref.Entities.reservation.Reservation;
import lombok.*;

import java.io.Serializable;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Parking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String adresse;
    private int capaciteTotale;
    private String description;
    private double latitude;
    private double longitude;
    private String image ;

    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Etage> etages;
}