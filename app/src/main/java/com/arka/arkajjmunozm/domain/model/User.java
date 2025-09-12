package com.arka.arkajjmunozm.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private int id;
    private String enterprise_id;
    private String name;
    private String email;
    private Date created_at;
    private Date updated_at;
}
