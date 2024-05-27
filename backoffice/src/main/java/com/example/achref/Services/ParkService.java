package com.example.achref.Services;


import com.example.achref.Entities.parking.Bloc;
import com.example.achref.Entities.parking.Etage;
import com.example.achref.Entities.parking.Parking;
import com.example.achref.Entities.parking.PlaceParking;

import com.example.achref.Entities.reservation.Reservation;
import com.example.achref.Entities.user.User;
import com.example.achref.QRCODE.QRCodeGenerator;
import com.example.achref.Repositories.parking.*;
import com.example.achref.Repositories.user.UserRepository;
import com.google.zxing.WriterException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkService {
    private final ParkingRepository parkingRepository;
    private final BlocRepository blocRepository;
    private final EtageRepository etageRepository;
    private final PlaceParkingRepository placeParkingRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final UserProfileService userProfileService;


    /* public void addParking(Parking parking) {
         parkingRepository.save(parking);
     }
 */
    public List<Parking> displayParking() {
        return parkingRepository.findAll();
    }

  /*  public void updateParking(Parking parking) {
        Parking p = parkingRepository.findById(parking.getId()).orElse(null);
        if (p.getEtages() != null) {
            parking.setEtages(p.getEtages());
        }

        parkingRepository.save(parking);
    }*/

    public void deleteParking(Long id) {
        Optional<Parking> parkingOptional = parkingRepository.findById(id);
        if (parkingOptional.isPresent()) {
            // Si la formation existe, supprimez-la de la base de données
            Parking formation = parkingOptional.get();

            // Supprimez l'image associée s'il y en a une
            if (formation.getImage() != null && !formation.getImage().isEmpty()) {
                try {
                    // Obtenez le chemin complet du dossier contenant l'image
                    String folderPath = "C:\\Users\\olkhaznaji\\Desktop\\StagePFE\\Frontend\\src\\assets\\images\\Formations\\";
                    String filePath = folderPath + formation.getImage();

                    // Supprimez le fichier image
                    Files.deleteIfExists(Paths.get(filePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            parkingRepository.delete(formation);
        } else {
        }
    }


    public Parking getParkingById(Long idParking) {
        return parkingRepository.findById(idParking).orElse(null);
    }


    public void addEtageToParking(Long parkingId, Etage etage) {
        Parking parking = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new IllegalArgumentException("Parking non trouvé avec l'ID : " + parkingId));

        // Vérification de la capacité totale des étages
        int totalCapacity = parking.getEtages().stream()
                .mapToInt(Etage::getCapaciteEtage)
                .sum();
        if (totalCapacity + etage.getCapaciteEtage() > parking.getCapaciteTotale()) {
            throw new IllegalArgumentException("La capacité totale des étages dépasse la capacité totale du parking.");
        }

        // Ajout de l'étage au parking
        etage.setParking(parking);
        parking.getEtages().add(etage);
        parkingRepository.save(parking);
    }


    public void updateEtage(Long etageId, Etage updatedEtage) {
        Etage etage = etageRepository.findById(etageId)
                .orElseThrow(() -> new IllegalArgumentException("Étage non trouvé avec l'ID : " + etageId));
        Parking parking = etage.getParking();
        int totalCapacity = parking.getEtages().stream()
                .mapToInt(Etage::getCapaciteEtage)
                .sum();
        int updatedTotalCapacity = totalCapacity - etage.getCapaciteEtage() + updatedEtage.getCapaciteEtage();

        // Vérification si la capacité totale des étages dépasse la capacité totale du parking
        if (updatedTotalCapacity > parking.getCapaciteTotale()) {
            throw new IllegalArgumentException("La capacité totale des étages dépasse la capacité totale du parking.");
        }

        // Mise à jour des détails de l'étage
        etage.setNumeroEtage(updatedEtage.getNumeroEtage());
        etage.setCapaciteEtage(updatedEtage.getCapaciteEtage());
        etage.setParking(updatedEtage.getParking());

        etageRepository.save(etage);
    }

    public List<Etage> displayEtage() {
        return etageRepository.findAll();
    }

    public void deleteEtage(Long id) {
        Optional<Etage> EtageOptional = etageRepository.findById(id);
        if (EtageOptional.isPresent()) {
            etageRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Le parking avec l'ID " + id + " n'existe pas !");
        }
    }

    public Etage getEtageById(Long idEtage) {
        return etageRepository.findById(idEtage).orElse(null);
    }


    public void addBlocToEtage(Long etageId, Bloc bloc) {
        Etage etage = etageRepository.findById(etageId)
                .orElseThrow(() -> new IllegalArgumentException("Étage non trouvé avec l'ID : " + etageId));
    /*    if (bloc.getEtage() != null) {
            throw new IllegalArgumentException("Le bloc est déjà associé à un autre étage.");
        }*/

        bloc.setEtage(etage);
        blocRepository.save(bloc);
    }


    public List<Bloc> displayBloc() {
        return blocRepository.findAll();
    }

    public void updateBloc(Long blocId, Bloc updatedBloc) {
        Bloc bloc = blocRepository.findById(blocId)
                .orElseThrow(() -> new IllegalArgumentException("Bloc non trouvé avec l'ID : " + blocId));

        if (bloc.getEtage() == null) {
            throw new IllegalArgumentException("Le bloc à mettre à jour n'est associé à aucun étage.");
        }
        bloc.setIdentifiantBloc(updatedBloc.getIdentifiantBloc());
        bloc.setEtage(updatedBloc.getEtage());
        blocRepository.save(bloc);
    }

    public void deleteBloc(Long id) {
        Optional<Bloc> blocOptional = blocRepository.findById(id);
        if (blocOptional.isPresent()) {
            blocRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Le BLoc avec l'ID " + id + " n'existe pas !");
        }
    }


    public Bloc getBlocById(Long idBloc) {
        return blocRepository.findById(idBloc).orElse(null);
    }


    public void addPlaceParkingToBloc(Long blocId, PlaceParking placeParking) {
        Bloc bloc = blocRepository.findById(blocId)
                .orElseThrow(() -> new IllegalArgumentException("Bloc non trouvé avec l'ID : " + blocId));
        placeParking.setBloc(bloc);
        placeParkingRepository.save(placeParking);
        System.out.println("Votre place est ajoutée avec succès.");
    }

    public List<PlaceParking> getAllPlaceParking() {
        return placeParkingRepository.findAll();
    }

    public Optional<PlaceParking> getPlaceParkingById(Long id) {
        return placeParkingRepository.findById(id);
    }

    public void updatePlaceParking(Long id, PlaceParking updatedPlaceParking) {
        PlaceParking placeParking = placeParkingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Place de parking non trouvée avec l'ID : " + id));
        placeParking.setNumeroPlace(updatedPlaceParking.getNumeroPlace());
        placeParking.setEstAccessibleHandicap(updatedPlaceParking.isEstAccessibleHandicap());
        placeParking.setEstReservee(updatedPlaceParking.isEstReservee());
        placeParking.setTypePlace(updatedPlaceParking.getTypePlace());
        placeParking.setBloc(updatedPlaceParking.getBloc());
        placeParkingRepository.save(placeParking);
    }

    public void deletePlaceParking(Long id) {
        placeParkingRepository.deleteById(id);
    }



    public List<Etage> getEtagesByParkingId(Long id) {
        return parkingRepository.findById(id).get().getEtages();
    }

    public List<Bloc> getBlocsByEtageId(Long id) {
        return etageRepository.findById(id).get().getBlocs();
    }

    public List<PlaceParking> getPlaceParkingByBloc(Long id) {
        return blocRepository.findById(id).get().getPlacesParking();
    }


    public List<Reservation> displayReservation() {
        return reservationRepository.findAll();
    }


   /* public void addReservationToParkingPlace(Integer userId,
                                             Long placeParkingId,
                                             Reservation reservation) {
        User user = userProfileService.getUserById(userId);
        PlaceParking placeParking = getPlaceParkingById(placeParkingId)
                .orElseThrow(() -> new IllegalArgumentException("Place de parking non trouvée avec l'ID : " + placeParkingId));
        LocalDateTime debutReservation = reservation.getDebutReservation();
        LocalDateTime finReservation = reservation.getFinReservation();

        reservation.setUtilisateur(user);
        reservation.setPlaceParking(placeParking);
        placeParking.setEstReservee(true);
        placeParkingRepository.save(placeParking);
        reservationRepository.save(reservation);


    }*/

    public void addReservationToParkingPlace(Integer userId,
                                             Long placeParkingId,
                                             Reservation reservation) throws IOException, WriterException {
        // Récupérer l'utilisateur
        User user = userProfileService.getUserById(userId);

        // Vérifier si l'utilisateur est null
        if (user == null) {
            throw new IllegalArgumentException("Utilisateur non trouvé avec l'ID : " + userId);
        }

        // Récupérer la place de parking
        PlaceParking placeParking = getPlaceParkingById(placeParkingId)
                .orElseThrow(() -> new IllegalArgumentException("Place de parking non trouvée avec l'ID : " + placeParkingId));

        // Vérifier si la place de parking est null
        if (placeParking == null) {
            throw new IllegalArgumentException("Place de parking non trouvée avec l'ID : " + placeParkingId);
        }

        reservation.setUtilisateur(user);
        reservation.setPlaceParking(placeParking);

        // Générer le QR code à partir des données de réservation
        String reservationData = generateReservationData(reservation);
        byte[] qrCodeImage = QRCodeGenerator.generateQRCodeImage(reservationData);

        // Enregistrer le QR code dans un fichier
        String qrCodeFileName = saveQRCodeImage(qrCodeImage);

        // Enregistrer le chemin d'accès au QR code avec la réservation
        reservation.setQrCodeFilePath(qrCodeFileName);
        placeParking.setEstReservee(true);
        placeParkingRepository.save(placeParking);
        reservationRepository.save(reservation);
    }

    public String generateReservationData(Reservation reservation) {
        // Récupérer les informations nécessaires de la réservation
        User utilisateur = reservation.getUtilisateur();
        PlaceParking placeParking = reservation.getPlaceParking();
        LocalDateTime debutReservation = reservation.getDebutReservation();
        LocalDateTime finReservation = reservation.getFinReservation();
        Long reservationn = reservation.getId();
        Boolean active = reservation.isEstActive();
        String qr = reservation.getQrCodeFilePath();


        // Vérifier si les objets utilisateur et placeParking sont null
        if (utilisateur == null || placeParking == null) {
            throw new IllegalArgumentException("L'utilisateur ou la place de parking est null dans la réservation");
        }

        // Construire la chaîne de données de réservation
        return "id: " + reservationn +"; debut_reservation: " + debutReservation + "est_active: " + active +  "; Fin: " + finReservation  + "; place_parking_id: " + placeParking.getId() +
                 "user_id: " + utilisateur.getIduserr() +"; qr_code_file_path: " + qr ;
    }

    private String saveQRCodeImage(byte[] qrCodeImage) throws IOException {
        Path qrCodeDirectoryPath = Paths.get("C:\\Users\\PC\\Desktop\\pfe\\front-achref\\src\\assets\\img\\parkings\\");
        Files.createDirectories(qrCodeDirectoryPath);

        String qrCodeFileName = "qr_code_" + System.currentTimeMillis() + ".png";
        Path qrCodeFilePath = ((Path) qrCodeDirectoryPath).resolve(qrCodeFileName);

        Files.write(qrCodeFilePath, qrCodeImage);

        return qrCodeFilePath.toString();
    }



    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        PlaceParking placeParking = reservation.getPlaceParking();
        placeParking.setEstReservee(false);
        placeParkingRepository.save(placeParking);
        reservationRepository.delete(reservation);
    }

    public double[] calculateOccupancyRateByBloc(Long blocId) {
        Bloc bloc = blocRepository.findById(blocId)
                .orElseThrow(() -> new EntityNotFoundException("Bloc non trouvé avec l'ID : " + blocId));

        List<PlaceParking> placesParking = bloc.getPlacesParking();

        int totalPlaces = placesParking.size();
        long occupiedPlaces = placesParking.stream()
                .filter(PlaceParking::isEstReservee)
                .count();

        double occupancyRate = (double) occupiedPlaces / totalPlaces * 100.0;
        double occupiedPercentage = (double) occupiedPlaces / totalPlaces * 100.0;
        double unoccupiedPercentage = 100.0 - occupiedPercentage;

        return new double[]{occupancyRate, occupiedPercentage, unoccupiedPercentage};
    }

    public List<PlaceParking> getAllPlaceParkinginEtage(long id){
        Etage etage=etageRepository.findById(id).orElse(null);
        List<PlaceParking> result=new ArrayList<>();
        for(Bloc b:etage.getBlocs()){
            result.addAll(getPlaceParkingByBloc(b.getId()));
        }
        return result;
    }

}