package com.arka.arkajjmunozm.infraestructure.adapter.in.web;
import java.util.List;

import com.arka.arkajjmunozm.application.usecase.SupplierService;
import com.arka.arkajjmunozm.domain.model.Supplier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<Supplier>> getSuppliers() {
        return ResponseEntity.ok(supplierService.allSuppliers());
    }

    @GetMapping("{id}")
    public ResponseEntity<Supplier> getSupplier(@PathVariable int id) {
        Supplier supplier = supplierService.getSupplier(id);
        return supplier != null ? ResponseEntity.ok(supplier) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Supplier> addSupplier(@Valid @RequestBody Supplier supplier) {
        return new ResponseEntity<>(supplierService.newSupplier(supplier), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable int id, @RequestBody Supplier supplier) {
        Supplier updated = supplierService.updateSupplier(id, supplier);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable int id) {
        return supplierService.deleteSupplier(id) ? ResponseEntity.ok().build() : ResponseEntity.noContent().build();
    }
}