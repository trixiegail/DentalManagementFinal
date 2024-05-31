package com.dentalmanagement.DentalManagement.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dentalmanagement.DentalManagement.Entity.DepartmentEntity;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

    @Query("SELECT d FROM DepartmentEntity d WHERE d.department = :department OR d.deptCode = :departmentCode")
    List<DepartmentEntity> findByDepartmentOrDepartmentCode(@Param("department") String department, @Param("departmentCode") String departmentCode);
}


