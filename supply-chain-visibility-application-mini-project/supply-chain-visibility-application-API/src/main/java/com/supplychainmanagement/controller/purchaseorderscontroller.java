package com.supplychainmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.supplychainmanagement.model.purchaseorders;
import com.supplychainmanagement.repo.purchaseordersrepo;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class purchaseorderscontroller {

    @Autowired
    private purchaseordersrepo purchaseOrdersRepository;

    @GetMapping("/getAllPurchaseOrders")
    public ResponseEntity<List<purchaseorders>> getAllPurchaseOrders() {
        try {
            List<purchaseorders> purchaseOrdersList = purchaseOrdersRepository.findAll();

            if (purchaseOrdersList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(purchaseOrdersList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPurchaseOrderById/{id}")
    public ResponseEntity<purchaseorders> getPurchaseOrderById(@PathVariable Long id) {
        Optional<purchaseorders> purchaseOrderData = purchaseOrdersRepository.findById(id);

        return purchaseOrderData.map(purchaseOrder ->
                        new ResponseEntity<>(purchaseOrder, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/addPurchaseOrder")
    public ResponseEntity<purchaseorders> addPurchaseOrder(@RequestBody purchaseorders purchaseOrder) {
        try {
            purchaseorders savedPurchaseOrder = purchaseOrdersRepository.save(purchaseOrder);
            return new ResponseEntity<>(savedPurchaseOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updatePurchaseOrderById/{id}")
    public ResponseEntity<purchaseorders> updatePurchaseOrderById(@PathVariable Long id, @RequestBody purchaseorders purchaseOrder) {
        Optional<purchaseorders> oldPurchaseOrderData = purchaseOrdersRepository.findById(id);

        if (oldPurchaseOrderData.isPresent()) {
            purchaseorders updatedPurchaseOrder = oldPurchaseOrderData.get();
            updatedPurchaseOrder.setItem(purchaseOrder.getItem());
            updatedPurchaseOrder.setQuantity(purchaseOrder.getQuantity());
            updatedPurchaseOrder.setSupplier(purchaseOrder.getSupplier());
            updatedPurchaseOrder.setPrice(purchaseOrder.getPrice());

            purchaseorders savedPurchaseOrder = purchaseOrdersRepository.save(updatedPurchaseOrder);
            return new ResponseEntity<>(savedPurchaseOrder, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deletePurchaseOrderById/{id}")
    public ResponseEntity<HttpStatus> deletePurchaseOrderById(@PathVariable Long id) {
        try {
            purchaseOrdersRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
