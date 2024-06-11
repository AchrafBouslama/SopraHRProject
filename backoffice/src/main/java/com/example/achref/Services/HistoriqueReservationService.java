package com.example.achref.Services;

import com.example.achref.Entities.parking.PlaceParking;
import com.example.achref.Entities.reservation.HistoriqueReservation;
import com.example.achref.Entities.reservation.Reservation;
import com.example.achref.Entities.user.User;
import com.example.achref.Repositories.parking.*;
import com.example.achref.Repositories.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class HistoriqueReservationService {
    private UserProfileService userProfileService;
    private final PlaceParkingRepository placeParkingRepository;
    private HistoriqueReservationRepository historiqueReservationRepository;
    private final ParkingRepository parkingRepository;
    private final BlocRepository blocRepository;
@Autowired
    public HistoriqueReservationService(UserProfileService userProfileService, PlaceParkingRepository placeParkingRepository,
            HistoriqueReservationRepository historiqueReservationRepository, ParkingRepository
            parkingRepository, BlocRepository blocRepository, EtageRepository etageRepository,
            ReservationRepository reservationRepository, UserRepository userRepository) {
        this.userProfileService = userProfileService;
        this.placeParkingRepository = placeParkingRepository;
        this.historiqueReservationRepository = historiqueReservationRepository;
        this.parkingRepository = parkingRepository;
        this.blocRepository = blocRepository;
        this.etageRepository = etageRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    private final EtageRepository etageRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public void addHistoriqueReservationToParkingPlace(Integer userId,
                                             Long placeParkingId,
                                             com.example.achref.Entities.reservation.HistoriqueReservation reservation) {
        User user = userProfileService.getUserById(userId);
        PlaceParking placeParking = getPlaceParkingById(placeParkingId)
                .orElseThrow(() -> new IllegalArgumentException("Place de parking non trouvée avec l'ID : " + placeParkingId));
        LocalDateTime debutReservation = reservation.getDebutReservation();
        LocalDateTime finReservation = reservation.getFinReservation();

        reservation.setUtilisateur(user);
        reservation.setPlaceParking(placeParking);
        placeParking.setEstReservee(true);
        placeParkingRepository.save(placeParking);
        historiqueReservationRepository.save(reservation);


    }
    public Optional<PlaceParking> getPlaceParkingById(Long id) {
        return placeParkingRepository.findById(id);
    }


    public void cancelReservation(Long reservationId) {
        HistoriqueReservation reservation = historiqueReservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        PlaceParking placeParking = reservation.getPlaceParking();
        placeParking.setEstReservee(false);
        placeParkingRepository.save(placeParking);
        historiqueReservationRepository.delete(reservation);
    }


    @Transactional
    public void addReservationToHistorique(Reservation reservation) {
        HistoriqueReservation historiqueReservation = new HistoriqueReservation();
        historiqueReservation.setUtilisateur(reservation.getUtilisateur());
        historiqueReservation.setPlaceParking(reservation.getPlaceParking());
        historiqueReservation.setDebutReservation(reservation.getDebutReservation());
        historiqueReservation.setFinReservation(reservation.getFinReservation());

        historiqueReservationRepository.save(historiqueReservation);
    }


}
