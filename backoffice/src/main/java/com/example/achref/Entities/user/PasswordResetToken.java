package com.example.achref.Entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idreset;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String token;

    private LocalDateTime expiryDate;

    public Long getIdreset() {
        return idreset;
    }

    public void setIdreset(Long idreset) {
        this.idreset = idreset;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    @JsonIgnore

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }
    @JsonIgnore


    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
    @JsonIgnore


    public boolean isExpired() {
        return expiryDate.isBefore(LocalDateTime.now());
    }
}

