package com.example.achref.Repositories.parking;

import com.example.achref.Entities.parking.PlaceParking;
import com.example.achref.Entities.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ReservationRepository extends JpaRepository<Reservation, Long > {

}
