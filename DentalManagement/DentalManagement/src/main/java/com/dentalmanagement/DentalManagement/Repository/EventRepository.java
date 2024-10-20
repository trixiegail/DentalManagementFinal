package com.dentalmanagement.DentalManagement.Repository;

import com.dentalmanagement.DentalManagement.Entity.Event;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findSlotsByDate(String date);
    Optional<Event> findByDateAndTime(String date, String time);
    Optional<Event> findById(Long id);
}
