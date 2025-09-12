package com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity;

import com.arka.arkajjmunozm.domain.model.Profile;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity // correlacion de datos - objetos//Instancia DB
@Table(name = "profile")
@Data // Getters, Setters, ToString()
@NoArgsConstructor // constructor vacio
@AllArgsConstructor // Inicializa todos los atributos
public class ProfileEntity extends Profile {
    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AI
    private int id;

    @NotNull
    boolean admin;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "user_profile")
    private List<UserEntity> user;
}