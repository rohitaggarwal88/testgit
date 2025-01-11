package com.rohi.first.movie_catalog_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rohi.first.movie_catalog_service.dao.DeptRepository;
import com.rohi.first.movie_catalog_service.entity.Dept;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class DeptService {

	@Autowired
	private DeptRepository deptRepository;
	
	public Dept createUser(Dept user){		
		return deptRepository.save(user);
	}
	public Dept getUserById(Long id){
		return deptRepository.findByDepartmentId(id);
	}
}
