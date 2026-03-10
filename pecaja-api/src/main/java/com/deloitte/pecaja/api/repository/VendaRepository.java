package com.deloitte.pecaja.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deloitte.pecaja.api.model.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Integer> {
    
}