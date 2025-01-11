package com.rohi.first.movie_catalog_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rohi.first.movie_catalog_service.dao.UserEmployeeRepository;
import com.rohi.first.movie_catalog_service.entity.UserEmployee;
import com.rohi.first.movie_catalog_service.models.Dept;
import com.rohi.first.movie_catalog_service.models.ResponseUserempDept;


import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class UserEmployeeService {

	@Autowired
	private UserEmployeeRepository userempRepository;
	@Autowired
    private RestTemplate restTemplate; 
	
	public UserEmployee createUser(UserEmployee user){		
		return userempRepository.save(user);
	}
	public ResponseUserempDept getUserById(Long id){
		UserEmployee userEmployee=userempRepository.findByEmployeeId(id);
		Dept dept = restTemplate.getForObject("http://dept-service/departments/dept/" + userEmployee.getDepartmentId(),
				Dept.class);
    	ResponseUserempDept res=new ResponseUserempDept();
    	res.setDept(dept);
    	res.setUserEmployee(userEmployee);
    	
		return res;
	}
}
