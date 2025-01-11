package com.rohi.first.movie_catalog_service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohi.first.movie_catalog_service.entity.UserEmployee;

public interface UserEmployeeRepository extends JpaRepository<UserEmployee, Long> 
{
	UserEmployee findByEmployeeId(Long employeeId);
}
