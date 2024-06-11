package com.example.achref.Services;
import com.example.achref.Entities.claims.Claim;
import com.example.achref.Entities.claims.ClaimResponse;
import com.example.achref.Entities.claims.Status;
import com.example.achref.Entities.parking.Bloc;
import com.example.achref.Entities.parking.PlaceParking;
import com.example.achref.Entities.user.User;
import com.example.achref.Repositories.claim.ClaimRepository;
import com.example.achref.Repositories.claim.ClaimResponseRepository;
import com.example.achref.Repositories.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import java.time.Instant;

@Service
public class ClaimService {
    private final UserRepository userRepository;
    private final ClaimRepository claimRepository;
    private final ClaimResponseRepository claimResponseRepository;

    public ClaimService(UserRepository userRepository, ClaimRepository claimRepository,
                        ClaimResponseRepository claimResponseRepository) {
        this.userRepository = userRepository;
        this.claimRepository = claimRepository;
        this.claimResponseRepository = claimResponseRepository;
    }



    @Transactional
    public void addReclamationToUser(Integer userId, Claim claim) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec l'ID : " + userId));

        claim.setUser(user);
        claim.setStatus(Status.EN_ATTENTE); // Définir le statut par défaut
        claim.setCreatedAt(LocalDateTime.now()); // Définir la date de création à l'instant actuel

        claimRepository.save(claim);
    }


    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    public void deleteClaim(Long claimId) {
        claimRepository.deleteById(claimId);
    }
    public void updateClaimStatus(Long claimId, Status newStatus) {
        Optional<Claim> optionalClaim = claimRepository.findById(claimId);
        if (optionalClaim.isPresent()) {
            Claim claim = optionalClaim.get();
            claim.setStatus(newStatus);
            claimRepository.save(claim);
        } else {
            throw new IllegalArgumentException("Réclamation introuvable avec l'ID : " + claimId);
        }
    }



    public void addReSponseToClaim(Long claimId, ClaimResponse claimResponse) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new IllegalArgumentException("Bloc non trouvé avec l'ID : " + claimId));

        claimResponse.setCreatedAt(Instant.now());
        claimResponse.setClaim(claim);
        claim.setStatus(Status.RESOLUE);
        claimResponseRepository.save(claimResponse);
        System.out.println("Votre réponse est ajoutée avec succès.");
    }


    public List<Claim> getClaimsByUserId(Long iduserr) {
        return claimRepository.findByUserIduserr(iduserr);
    }



    public List<ClaimResponse> getResponsesByClaimId(Long claimId) {
        return claimResponseRepository.findByClaimId(claimId);
    }




}
