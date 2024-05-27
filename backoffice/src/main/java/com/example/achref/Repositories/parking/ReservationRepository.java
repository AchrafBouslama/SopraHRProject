package com.example.achref.Repositories.parking;

import com.example.achref.Entities.parking.PlaceParking;
import com.example.achref.Entities.reservation.Reservation;
import com.example.achref.Entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ReservationRepository extends JpaRepository<Reservation, Long > {


    List<Reservation> findByUtilisateur_Iduserr(Integer utilisateurId); // Utilisez iduserr pour correspondre au champ dans l'entité User

}
