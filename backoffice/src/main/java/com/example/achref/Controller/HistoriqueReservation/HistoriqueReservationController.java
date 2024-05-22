package com.example.achref.Controller.HistoriqueReservation;
import com.example.achref.Entities.reservation.HistoriqueReservation;
import com.example.achref.Entities.reservation.Reservation;
import com.example.achref.Entities.user.User;
import com.example.achref.Services.HistoriqueReservationService;
import com.example.achref.Services.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/history")

public class HistoriqueReservationController {
    private final HistoriqueReservationService historiqueReservationService;


    @PostMapping("/add-to-parking-place/{userId}/{placeParkingId}")
    public ResponseEntity<?> addHistoriqueReservationToParkingPlace(@PathVariable Integer userId,
                                                                    @PathVariable Long placeParkingId,
                                                                    @RequestBody HistoriqueReservation reservation) {
        try {
            historiqueReservationService.addHistoriqueReservationToParkingPlace(userId, placeParkingId, reservation);
            return ResponseEntity.ok("Historique de réservation ajouté avec succès à la place de parking avec l'ID : " + placeParkingId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout de l'historique de réservation à la place de parking : " + e.getMessage());
        }
    }


    @PostMapping("/add-reservation-to-historique")
    public ResponseEntity<?> addReservationToHistorique(@RequestBody Reservation reservation) {
        try {
            historiqueReservationService.addReservationToHistorique(reservation);
            return ResponseEntity.ok("Réservation ajoutée à l'historique avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout de la réservation à l'historique : " + e.getMessage());
        }
    }


}
