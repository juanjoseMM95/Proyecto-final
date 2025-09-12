package com.arka.arkajjmunozm.application.usecase;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.arka.arkajjmunozm.domain.model.Supplier;
import com.arka.arkajjmunozm.domain.port.in.ISupplierService;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.SupplierEntity;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierService implements ISupplierService {
    private final SupplierRepository supplierRepository;

    private Supplier mapToSupplier(SupplierEntity entity) {
        return new Supplier(
                entity.getId(),
                entity.getName(),
                entity.getEmail()
        );
    }

    private SupplierEntity mapToEntity(Supplier supplier) {
        return new SupplierEntity(
                supplier.getId(),
                supplier.getName(),
                supplier.getEmail(),
                null
        );
    }

    @Override
    public List<Supplier> allSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(this::mapToSupplier)
                .collect(Collectors.toList());
    }

    @Override
    public Supplier newSupplier(Supplier supplier) {
        SupplierEntity entity = mapToEntity(supplier);
        SupplierEntity savedEntity = supplierRepository.save(entity);
        return mapToSupplier(savedEntity);
    }

    @Override
    public Supplier getSupplier(int id) {
        return supplierRepository.findById(id)
                .map(this::mapToSupplier)
                .orElse(null);
    }

    @Override
    public Supplier updateSupplier(int id, Supplier supplier) {
        Optional<SupplierEntity> supplierExist = supplierRepository.findById(id);
        if (supplierExist.isPresent()) {
            SupplierEntity entity = mapToEntity(supplier);
            entity.setId(id);
            SupplierEntity updatedEntity = supplierRepository.save(entity);
            return mapToSupplier(updatedEntity);
        }
        return null;
    }

    @Override
    public boolean deleteSupplier(int id) {
        Optional<SupplierEntity> supplierExist = supplierRepository.findById(id);
        if (supplierExist.isPresent()) {
            supplierRepository.deleteById(id);
            return true;
        }
        return false;
    }
}