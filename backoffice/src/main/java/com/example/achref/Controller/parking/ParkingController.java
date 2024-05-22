package com.example.achref.Controller.parking;

import com.example.achref.Controller.auth.EmailService;
import com.example.achref.Entities.parking.Bloc;
import com.example.achref.Entities.parking.Etage;
import com.example.achref.Entities.parking.Parking;
import com.example.achref.Entities.parking.PlaceParking;
import com.example.achref.Entities.reservation.Reservation;
import com.example.achref.Entities.user.User;
import com.example.achref.QRCODE.QRCodeGenerator;
import com.example.achref.Repositories.parking.BlocRepository;
import com.example.achref.Repositories.parking.ParkingRepository;
import com.example.achref.Services.ParkService;
import com.google.zxing.WriterException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.mysql.cj.conf.PropertyKey.logger;

@CrossOrigin(origins="*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Parking")
public class ParkingController {

    private final ParkService parkService;
    private final ParkingRepository parkingRepository;
    private final BlocRepository blocRepository;
    private final EmailService emailService;

    @PostMapping("/addParking")
    public ResponseEntity<?>  addParking(@RequestBody Parking parking
    ) {


            Parking savedParking =parkingRepository.save(parking);
            return new ResponseEntity<>(savedParking, HttpStatus.CREATED);

    }
    @GetMapping("/displayParking")
    public List<Parking> displayParking(){
        return parkService.displayParking();
    }
    /* @PutMapping("/updateParking")
     public void updateParking(@RequestBody Parking parking,@RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
         parkService.updateParking(parking,imageFile);
     }*/
    @PutMapping("/updateParking/{parkingId}")
    public ResponseEntity<?> updateParking(
            @PathVariable Long parkingId,
            @RequestParam("nom") String nom,
            @RequestParam("adresse") String adresse,
            @RequestParam("capaciteTotale") int capaciteTotale,
            @RequestParam("description") String description,
            @RequestParam("latitude") int latitude,
            @RequestParam("longitude") int longitude,
            @RequestParam(value = "image", required = false) MultipartFile image // Marquez le paramètre comme facultatif pour autoriser les mises à jour sans changement d'image
    ) {
        // Vérifiez si la formation existe
        Parking parking = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new EntityNotFoundException("Formation non trouvée avec l'ID : " + parkingId));

        // Mettez à jour les propriétés de la formation avec les nouvelles valeurs
        parking.setNom(nom);
        parking.setAdresse(adresse);

        parking.setCapaciteTotale(capaciteTotale);
        parking.setLatitude(latitude);
        parking.setDescription(description);
        parking.setLongitude(longitude);

        // Vérifiez s'il y a une nouvelle image à enregistrer
        if (image != null && !image.isEmpty()) {
            try {
                String originalFilename = image.getOriginalFilename();
                String uniqueFileName = UUID.randomUUID() + "_" + System.currentTimeMillis() + "_" + originalFilename;

                // Obtenez le chemin complet du dossier pour enregistrer l'image
                String folderPath = "C:\\Users\\PC\\Desktop\\pfe\\front-achref\\src\\assets\\img\\parkings\\";
                String filePath = folderPath + uniqueFileName;

                // Enregistrez le fichier dans le dossier spécifié
                Path path = Paths.get(filePath);
                Files.write(path, image.getBytes());

                // Supprimez l'ancienne image s'il y en a une
                if (parking.getImage() != null && !parking.getImage().isEmpty()) {
                    String oldFilePath = folderPath + parking.getImage();
                    Files.deleteIfExists(Paths.get(oldFilePath));
                }

                // Mettez à jour le nom de fichier de l'image dans la base de données
                parking.setImage(uniqueFileName);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Erreur lors de l'enregistrement de la nouvelle image", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // Sauvegardez les modifications dans la base de données
        Parking updatedParking = parkingRepository.save(parking);

        return new ResponseEntity<>(updatedParking, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteParking(@PathVariable Long id) {
        parkService.deleteParking(id);
        return ResponseEntity.ok("Parking supprimé avec succès !");
    }

    @GetMapping("/displayParking/{id}")
    public Parking displayUserById(@PathVariable Long id){
        return parkService.getParkingById(id);
    }

    @PostMapping("/addEtageToParking/{parkingId}")
    public void addEtageToParking(@PathVariable Long parkingId, @RequestBody Etage etage) {
        try {
            parkService.addEtageToParking(parkingId, etage);
        } catch (IllegalArgumentException e) {
            // Vous pouvez gérer l'exception ici, par exemple, en journalisant un message
            e.printStackTrace();
        }
    }

    @PutMapping("/updateEtage/{id}")
    public ResponseEntity<Void> updateEtage(@PathVariable("id") Long etageId, @RequestBody Etage etageDetails) {
        try {
            parkService.updateEtage(etageId, etageDetails);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/displayEtage")
    public List<Etage> displayEtage(){
        return parkService.displayEtage();
    }

    @DeleteMapping("/deleteEtage/{id}")
    public ResponseEntity<String> deleteEtage(@PathVariable Long id) {
        parkService.deleteEtage(id);
        return ResponseEntity.ok("Parking supprimé avec succès !");
    }

    @GetMapping("/displayEtage/{idEtage}") // Utilisez idEtage ici
    public Etage displayEtageById(@PathVariable Long idEtage){
        return parkService.getEtageById(idEtage);
    }



    @PostMapping("/addBlocToEtage/{etageId}")
    public void addBlocToEtage(@PathVariable Long etageId, @RequestBody Bloc bloc) {
        try {
            parkService.addBlocToEtage(etageId, bloc);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();

        }

    }

    @GetMapping("/displayBloc")
    public List<Bloc> displayBloc(){
        return parkService.displayBloc();
    }

    @PutMapping("/updateBloc/{blocId}")
    public void updateBloc(@PathVariable Long blocId, @RequestBody Bloc updatedBloc) {
        try {
            parkService.updateBloc(blocId, updatedBloc);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();

        }
    }


    @DeleteMapping("/deleteBloc/{id}")
    public ResponseEntity<String> deleteBloc(@PathVariable Long id) {
        parkService.deleteBloc(id);
        return ResponseEntity.ok("Parking supprimé avec succès !");
    }

    @GetMapping("/displayBloc/{idBloc}") // Utilisez idEtage ici
    public Bloc getBlocById(@PathVariable Long idBloc){
        return parkService.getBlocById(idBloc);
    }




    @PostMapping("/addPlaceParkingToBloc/{blocId}")
    public void addPlaceParking(@PathVariable Long blocId, @RequestBody PlaceParking placeParking) {
        parkService.addPlaceParkingToBloc(blocId, placeParking);
    }

    @GetMapping("/displayPlaceParking")
    public List<PlaceParking> displayPlaceParking(){
        return parkService.getAllPlaceParking();
    }

    @GetMapping("/displayPlaceParking/{id}")
    public ResponseEntity<PlaceParking> displayPlaceParkingById(@PathVariable Long id) {
        Optional<PlaceParking> placeParking = parkService.getPlaceParkingById(id);
        return placeParking.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/updatePlaceParking/{id}")
    public void  updatePlaceParking(@PathVariable Long id, @RequestBody PlaceParking updatedPlaceParking){
        parkService.updatePlaceParking(id, updatedPlaceParking);
    }

    @DeleteMapping("/deletePlaceParking/{id}")
    public ResponseEntity<String> deletePlaceParking(@PathVariable Long id) {
        parkService.deletePlaceParking(id);
        return ResponseEntity.ok("Place de parking supprimée avec succès !");
    }





    @GetMapping("/displayEtages/{idParking}")
    public List<Etage> displayEtagesByIdParking(@PathVariable Long idParking){
        return parkService.getEtagesByParkingId(idParking);
    }
    @GetMapping("/displayBlocs/{idEtage}")
    public List<Bloc> displayBlocsByIdParking(@PathVariable Long idEtage){
        return parkService.getBlocsByEtageId(idEtage);
    }
    @GetMapping("/displayPlaceParkings/{idBloc}")
    public List<PlaceParking> displayPlaceParkingsByidBloc(@PathVariable Long idBloc){
        return parkService.getPlaceParkingByBloc(idBloc);
    }




    @GetMapping("/occupancyRateForAllBlocs")
    public ResponseEntity<Map<String, Double>> getOccupancyRateForAllBlocs() {
        List<Bloc> blocs = blocRepository.findAll(); // Récupérer tous les blocs

        List<PlaceParking> allPlaces = blocs.stream()
                .flatMap(bloc -> bloc.getPlacesParking().stream()) // Récupérer toutes les places de parking
                .collect(Collectors.toList());

        int totalPlaces = allPlaces.size();
        long occupiedPlaces = allPlaces.stream().filter(PlaceParking::isEstReservee).count();

        double occupancyRate = (double) occupiedPlaces / totalPlaces * 100.0;
        double occupiedPercentage = (double) occupiedPlaces / totalPlaces * 100.0;
        double unoccupiedPercentage = 100.0 - occupiedPercentage;

        Map<String, Double> response = new HashMap<>();
        response.put("occupancyRate", occupancyRate);
        response.put("occupiedPercentage", occupiedPercentage);
        response.put("unoccupiedPercentage", unoccupiedPercentage);

        return ResponseEntity.ok(response);
    }



    @GetMapping("/displayReservation")
    public List<Reservation> displayReservation(){
        return parkService.displayReservation();
    }


    @PostMapping("/addReservation/{userId}/{placeParkingId}")
    public void addReservationToParkingPlace(
            @PathVariable Integer userId,
            @PathVariable Long placeParkingId,
            @RequestBody Reservation reservation) {
        try {
            parkService.addReservationToParkingPlace(userId, placeParkingId, reservation);

            // Récupérer l'utilisateur de la réservation
            User user = reservation.getUtilisateur();

            // Vérifier si l'utilisateur est null
            if (user != null) {
                // Générer le QR code à partir des données de réservation
                String reservationData = parkService.generateReservationData(reservation);
                byte[] qrCodeImage = QRCodeGenerator.generateQRCodeImage(reservationData);

                // Envoyer le QR code par e-mail à l'utilisateur
                emailService.sendQRCodeByEmail(user.getEmail(), qrCodeImage);
            }
        } catch (IOException | WriterException e) {
            // Gérer les exceptions ici
            e.printStackTrace(); // Imprimez la trace de la pile pour le suivi
            // Vous pouvez également retourner une réponse appropriée à l'utilisateur
            // par exemple, avec un code d'erreur ou un message explicatif
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la génération du code QR");
        }
    }


    @DeleteMapping("/deleteReservation/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        parkService.cancelReservation(id);
        return ResponseEntity.ok("Réservation annulée avec succès !");
    }


}
