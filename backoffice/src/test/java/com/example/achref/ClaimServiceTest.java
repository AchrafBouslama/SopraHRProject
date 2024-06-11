package com.example.achref;

import com.example.achref.Entities.claims.Claim;
import com.example.achref.Entities.claims.ClaimResponse;
import com.example.achref.Entities.claims.Status;
import com.example.achref.Entities.user.User;
import com.example.achref.Repositories.claim.ClaimRepository;
import com.example.achref.Repositories.claim.ClaimResponseRepository;
import com.example.achref.Repositories.user.UserRepository;
import com.example.achref.Services.ClaimService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClaimServiceTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private ClaimRepository claimRepository;

    @Mock
    private ClaimResponseRepository claimResponseRepository;

    @InjectMocks
    private ClaimService claimService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddReclamationToUser() {
        User user = new User();
        user.setIduserr(1);
        Claim claim = new Claim();
        claim.setId(1L);
        claim.setStatus(Status.EN_ATTENTE);
        claim.setCreatedAt(LocalDateTime.now());

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        claimService.addReclamationToUser(1, claim);

        verify(claimRepository, times(1)).save(any(Claim.class));
    }

    @Test
    void testGetAllClaims() {
        List<Claim> claims = new ArrayList<>();
        claims.add(new Claim());
        claims.add(new Claim());

        when(claimRepository.findAll()).thenReturn(claims);

        List<Claim> result = claimService.getAllClaims();

        assertEquals(2, result.size());
    }

    @Test
    void testDeleteClaim() {
        Long claimId = 1L;

        claimService.deleteClaim(claimId);

        verify(claimRepository, times(1)).deleteById(claimId);
    }

    @Test
    void testUpdateClaimStatus() {
        Long claimId = 1L;
        Status newStatus = Status.TRAITEE;

        Claim claim = new Claim();
        claim.setId(claimId);
        claim.setStatus(Status.EN_ATTENTE);

        when(claimRepository.findById(claimId)).thenReturn(Optional.of(claim));

        claimService.updateClaimStatus(claimId, newStatus);

        assertEquals(newStatus, claim.getStatus());
    }

    @Test
    void testUpdateClaimStatusClaimNotFound() {
        Long claimId = 1L;
        Status newStatus = Status.EN_ATTENTE;

        when(claimRepository.findById(claimId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            claimService.updateClaimStatus(claimId, newStatus);
        });
    }


    @Test
    void testAddReSponseToClaim() {
        Long claimId = 1L;
        Claim claim = new Claim();
        claim.setId(claimId);
        claim.setStatus(Status.EN_ATTENTE);

        ClaimResponse claimResponse = new ClaimResponse();
        claimResponse.setId(1L);
        claimResponse.setCreatedAt(Instant.now());

        when(claimRepository.findById(claimId)).thenReturn(Optional.of(claim));

        claimService.addReSponseToClaim(claimId, claimResponse);

        assertEquals(Status.RESOLUE, claim.getStatus());
        verify(claimResponseRepository, times(1)).save(any(ClaimResponse.class));
    }

    @Test
    void testGetClaimsByUserId() {
        Long userId = 1L;
        List<Claim> claims = new ArrayList<>();
        claims.add(new Claim());
        claims.add(new Claim());

        when(claimRepository.findByUserIduserr(userId)).thenReturn(claims);

        List<Claim> result = claimService.getClaimsByUserId(userId);

        assertEquals(2, result.size());
    }

    @Test
    void testGetResponsesByClaimId() {
        Long claimId = 1L;
        List<ClaimResponse> responses = new ArrayList<>();
        responses.add(new ClaimResponse());
        responses.add(new ClaimResponse());

        when(claimResponseRepository.findByClaimId(claimId)).thenReturn(responses);

        List<ClaimResponse> result = claimService.getResponsesByClaimId(claimId);

        assertEquals(2, result.size());
    }
}
