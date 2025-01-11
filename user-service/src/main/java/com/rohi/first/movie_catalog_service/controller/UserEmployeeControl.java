package com.rohi.first.movie_catalog_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rohi.first.movie_catalog_service.entity.UserEmployee;
import com.rohi.first.movie_catalog_service.models.Dept;
import com.rohi.first.movie_catalog_service.models.ResponseUserempDept;
import com.rohi.first.movie_catalog_service.service.UserEmployeeService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping(value="/users")
public class UserEmployeeControl {

	@Autowired
	private UserEmployeeService userempService;
	
	
	@RequestMapping(method=RequestMethod.POST,value="adduseremp")
	public UserEmployee addUser(@RequestBody UserEmployee user) {
		//System.out.println(fService.tesname());
		return userempService.createUser(user);
	}
	@RequestMapping(method=RequestMethod.GET,value="/dept/{id}")
    @CircuitBreaker(name = "inventory", fallbackMethod = "testfaMethod")
	public ResponseUserempDept getUserById(@PathVariable Long id) {		
		return userempService.getUserById(id);
	}
	@RequestMapping(method=RequestMethod.GET,value="/testfadept")
	public ResponseUserempDept testfaMethod(Long id,Exception ex){
    	System.out.println(" return from fallback method successfully customized one");
    	ResponseUserempDept d1=new ResponseUserempDept();
    	Dept d=new Dept();
    	d.setDepartmentName("falback");
    	d.setDepartmentAddress("falbackadd");
    	d.setDepartmentCode("falbackcde");
    	d1.setDept(d);
    	return d1;
    	
    }
}
