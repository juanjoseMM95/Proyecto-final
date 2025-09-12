package com.arka.arkajjmunozm.domain.model;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Order {
    private int id;
    private double totalAmount;
    private Date date;
    private boolean send;
}
