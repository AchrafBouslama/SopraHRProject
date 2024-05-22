package com.example.achref.Repositories.parking;

import com.example.achref.Entities.reservation.HistoriqueReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriqueReservationRepository extends JpaRepository<HistoriqueReservation,Long> {

}
