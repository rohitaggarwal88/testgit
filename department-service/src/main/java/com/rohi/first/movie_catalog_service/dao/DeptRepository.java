package com.rohi.first.movie_catalog_service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohi.first.movie_catalog_service.entity.Dept;

public interface DeptRepository extends JpaRepository<Dept, Long> 
{
	Dept findByDepartmentId(Long departmentId);
}
