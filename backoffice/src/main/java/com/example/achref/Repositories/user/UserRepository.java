package com.example.achref.Repositories.user;

import com.example.achref.Entities.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

  /*  Optional<User> findByEmail(String email);*/
  Optional<User> findByEmail(String email);
}
