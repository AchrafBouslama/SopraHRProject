package com.example.achref.mapper;

import com.example.achref.Entities.reservation.HistoriqueReservation;
import com.example.achref.Entities.reservation.Reservation;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HistoriqueReservationMapper  {
    public HistoriqueReservation mapReservationToHistory(Reservation reservation){
        return HistoriqueReservation.builder()
                .utilisateur(reservation.getUtilisateur())
                .debutReservation(reservation.getDebutReservation())
                .finReservation(reservation.getFinReservation())
                .placeParking(reservation.getPlaceParking())
                .build();
    }
    public List<HistoriqueReservation> mapListReservationToListHistory(List<Reservation> reservations) {
        List<HistoriqueReservation> historiqueReservations=new ArrayList<HistoriqueReservation>();
        for(Reservation element :reservations){
            historiqueReservations.add(mapReservationToHistory(element));
        }
        return historiqueReservations;
    }

}
