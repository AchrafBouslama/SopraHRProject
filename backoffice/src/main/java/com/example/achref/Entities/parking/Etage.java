package com.example.achref.Entities.parking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.achref.Entities.reservation.Reservation;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Etage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numeroEtage;
    private int capaciteEtage;

    @ManyToOne
    @JoinColumn(name = "parking_id")

    private Parking parking;

    @OneToMany(mappedBy = "etage", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Bloc> blocs;
}