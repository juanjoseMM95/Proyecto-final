package com.arka.arkajjmunozm.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Profile {
    private int id;
    boolean admin;
}
