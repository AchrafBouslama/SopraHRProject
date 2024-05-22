package com.example.achref.Entities.reservation;

import com.example.achref.Entities.parking.PlaceParking;
import com.example.achref.Entities.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User utilisateur;

    @ManyToOne
    @JoinColumn(name = "placeParking_id")
    private PlaceParking placeParking;

    private LocalDateTime debutReservation;
    private LocalDateTime finReservation;
    private boolean estActive;
    private String   qrCodeFilePath;
}
