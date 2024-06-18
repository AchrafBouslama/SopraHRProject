package com.example.achref.Entities.claims;

import com.example.achref.Entities.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Claim implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy="claim",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private ClaimResponse response;
}
