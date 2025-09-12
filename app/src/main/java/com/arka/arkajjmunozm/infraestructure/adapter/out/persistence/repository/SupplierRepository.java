package com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.SupplierEntity;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Integer>{

}
