package com.example.achref.Repositories.claim;

import com.example.achref.Entities.claims.Claim;
import com.example.achref.Entities.claims.ClaimResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimResponseRepository extends JpaRepository<ClaimResponse, Long> {


    List<ClaimResponse> findByClaimId(Long claimId);
}


