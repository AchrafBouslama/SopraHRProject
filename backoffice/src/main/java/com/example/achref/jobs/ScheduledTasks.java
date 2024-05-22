package com.example.achref.jobs;

import com.example.achref.Entities.parking.PlaceParking;
import com.example.achref.Entities.reservation.HistoriqueReservation;
import com.example.achref.Entities.reservation.Reservation;
import com.example.achref.Repositories.parking.HistoriqueReservationRepository;
import com.example.achref.Repositories.parking.PlaceParkingRepository;
import com.example.achref.Repositories.parking.ReservationRepository;
import com.example.achref.mapper.HistoriqueReservationMapper;
import com.mysql.cj.log.Log;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class ScheduledTasks {
    private final ReservationRepository reservationRepository;
    private final HistoriqueReservationRepository historiqueReservationRepository;
    private final HistoriqueReservationMapper historiqueReservationMapper;

    private final PlaceParkingRepository placeParkingRepository;

    @Autowired
    public ScheduledTasks(ReservationRepository reservationRepository,
                          HistoriqueReservationRepository historiqueReservationRepository,
                          HistoriqueReservationMapper historiqueReservationMapper, PlaceParkingRepository placeParkingRepository) {
        this.reservationRepository = reservationRepository;
        this.historiqueReservationRepository = historiqueReservationRepository;
        this.historiqueReservationMapper = historiqueReservationMapper;
        this.placeParkingRepository = placeParkingRepository;
    }

    @Scheduled(cron = "0 0 17 * * ?")
    public void SaveHistory(){
        List<Reservation> reservations = reservationRepository.findAll();
        log.info("list of resrvations",reservations);
        List<HistoriqueReservation> HistoryResrvations=historiqueReservationMapper.mapListReservationToListHistory(reservations) ;
        log.info(" list of HistoryReservations",HistoryResrvations);
        historiqueReservationRepository.saveAll(HistoryResrvations);
        log.info("save list history");
        reservationRepository.deleteAll();
        log.info("deleted resrvation");
    }
    @Scheduled(cron = "0 0 17 * * ?")
    public void saveHistoryAndResetParking() {
        // Your existing logic to save history here

        // Update all parking spots to estReservee = false
        List<PlaceParking> allParkingSpots = placeParkingRepository.findAll();
        for (PlaceParking spot : allParkingSpots) {
            spot.setEstReservee(false);
        }
        placeParkingRepository.saveAll(allParkingSpots);
        log.info("All parking spots marked as available");
    }

}
