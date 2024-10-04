package com.dentalmanagement.DentalManagement.Repository;

import com.dentalmanagement.DentalManagement.Entity.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactUsRepository extends JpaRepository<ContactUs, Long> {
}

