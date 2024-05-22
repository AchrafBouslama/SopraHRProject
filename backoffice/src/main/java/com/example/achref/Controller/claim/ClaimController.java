package com.example.achref.Controller.claim;

import com.example.achref.Entities.claims.Claim;
import com.example.achref.Entities.claims.ClaimResponse;
import com.example.achref.Entities.claims.Status;
import com.example.achref.Entities.parking.PlaceParking;
import com.example.achref.Entities.user.User;
import com.example.achref.Services.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/claims")
public class ClaimController {

    private final ClaimService claimService;

    @PostMapping("claims/{userId}")
    public void addReclamationToUser(@PathVariable("userId") Integer userId,
                                     @RequestBody Claim claim) {
        try {
            claimService.addReclamationToUser(userId, claim);
        } catch (Exception e) {
        }
    }
    @GetMapping("/displayClaims")
    public List<Claim> getAllClaims() {
        return claimService.getAllClaims();
    }

    @DeleteMapping("deleteClaim/{claimId}")
    public ResponseEntity<?> deleteClaim(@PathVariable Long claimId) {
        try {
            claimService.deleteClaim(claimId);
            return ResponseEntity.ok("Réclamation supprimée avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la suppression de la réclamation : " + e.getMessage());
        }
    }

    @PutMapping("/{claimId}/status")
    public ResponseEntity<?> updateClaimStatus(@PathVariable Long claimId, @RequestParam Status newStatus) {
        try {
            claimService.updateClaimStatus(claimId, newStatus);
            return ResponseEntity.ok("Statut de la réclamation mis à jour avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour du statut de la réclamation : " + e.getMessage());
        }
    }


    @PostMapping("response/{claimId}")
    public void addResponseToClaim(@PathVariable Long claimId, @RequestBody ClaimResponse claimResponse) {
        claimService.addReSponseToClaim(claimId, claimResponse);
    }

    @GetMapping("/user/{iduserr}")
    public ResponseEntity<List<Claim>> getClaimsByUserId(@PathVariable Long iduserr) {
        List<Claim> claims = claimService.getClaimsByUserId(iduserr);
        if (claims.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(claims, HttpStatus.OK);
    }


    @GetMapping("responses/{claimId}")
    public ResponseEntity<List<ClaimResponse>> getResponsesByClaimId(@PathVariable Long claimId) {
        List<ClaimResponse> responses = claimService.getResponsesByClaimId(claimId);
        if (responses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }



}
