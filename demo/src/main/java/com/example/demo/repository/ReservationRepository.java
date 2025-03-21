package com.example.demo.repository;

import com.example.demo.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Możesz dodać własne metody, np. wyszukiwanie rezerwacji po użytkowniku lub książce
}
