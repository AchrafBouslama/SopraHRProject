package com.example.achref.Entities.user;

import com.example.achref.Entities.claims.Claim;
import com.example.achref.Entities.claims.ClaimResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Getter
@Setter


@Table(name = "user")

public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iduserr ;
    public Integer getIduserr() {
        return iduserr;
    }

    public void setIduserr(Integer iduserr) {
        this.iduserr = iduserr;
    }
    private String firstname ;
    private String lastname ;
    private boolean isEnabled;
    private String email ;
    private String password ;
    @Enumerated (EnumType.STRING)
    private Role role ;
    private String image;
    private String verificationCode;

    @OneToMany(mappedBy="user",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<Claim> claims;




    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore

    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore

    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore

    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
