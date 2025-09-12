package com.arka.arkajjmunozm.domain.port.in;

import com.arka.arkajjmunozm.domain.model.Supplier;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.SupplierEntity;

import java.util.List;

public interface ISupplierService {
    List<Supplier> allSuppliers();
    Supplier newSupplier(Supplier supplier);
    Supplier getSupplier(int id);
    Supplier updateSupplier(int id, Supplier supplier);
    boolean deleteSupplier(int id);
}
