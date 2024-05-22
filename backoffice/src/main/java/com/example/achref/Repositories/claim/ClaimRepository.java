package com.example.achref.Repositories.claim;

import com.example.achref.Entities.claims.Claim;
import com.example.achref.Entities.parking.Bloc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByUserIduserr(Long userId);
}
