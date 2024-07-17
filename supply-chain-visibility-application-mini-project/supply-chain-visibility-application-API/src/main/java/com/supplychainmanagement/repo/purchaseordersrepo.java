package com.supplychainmanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supplychainmanagement.model.purchaseorders;

@Repository
public interface purchaseordersrepo extends JpaRepository<purchaseorders, Long>{

}