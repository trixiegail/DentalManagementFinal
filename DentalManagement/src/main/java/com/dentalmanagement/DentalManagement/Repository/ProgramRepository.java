package com.dentalmanagement.DentalManagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dentalmanagement.DentalManagement.Entity.ProgramEntity;

public interface ProgramRepository extends JpaRepository<ProgramEntity, Long>{
	@Query("SELECT p FROM ProgramEntity p WHERE p.program = :program OR p.programCode = :programCode")
    List<ProgramEntity> findByProgramOrProgramCode(@Param("program") String program, @Param("programCode") String programCode);
}
