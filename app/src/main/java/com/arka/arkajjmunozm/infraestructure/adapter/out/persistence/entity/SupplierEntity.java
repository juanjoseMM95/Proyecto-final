package com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity;

import com.arka.arkajjmunozm.domain.model.Supplier;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "supplier")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierEntity extends Supplier {
    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AI
    private int id;

    @NotBlank(message = "name is mandatory") // valida name no venga vacio se usa comunmente para string
    @Size(max = 45)
    private String name;

    @NotBlank
    @Email(message = "El correo electrónico no es válido")
    private String email;

    //relación 1:n con entidad product
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "product_supplier")
    private List<ProductEntity> products;

}
