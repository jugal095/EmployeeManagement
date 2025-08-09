package com.jugal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jugal.model.Department;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);
}
