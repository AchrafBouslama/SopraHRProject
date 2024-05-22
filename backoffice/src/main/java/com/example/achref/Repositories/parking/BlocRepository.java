package com.example.achref.Repositories.parking;

import com.example.achref.Entities.parking.Bloc;
import com.example.achref.Entities.parking.Etage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlocRepository  extends JpaRepository<Bloc, Long> {
}
