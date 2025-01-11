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

import com.rohi.first.movie_catalog_service.entity.Dept;
import com.rohi.first.movie_catalog_service.service.DeptService;

@RestController
@RequestMapping(value="/departments")
public class DeptControl {

	@Autowired
	private DeptService depService;
	
	
	@RequestMapping(method=RequestMethod.POST,value="adddept")
	public Dept addUser(@RequestBody Dept user) {
		//System.out.println(fService.tesname());
		return depService.createUser(user);
	}
	@RequestMapping(method=RequestMethod.GET,value="/dept/{id}")
	public Dept getUserById(@PathVariable Long id) {
		
		return depService.getUserById(id);
	}
	
}
