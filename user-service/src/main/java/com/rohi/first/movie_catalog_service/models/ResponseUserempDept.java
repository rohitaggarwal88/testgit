package com.rohi.first.movie_catalog_service.models;

import com.rohi.first.movie_catalog_service.entity.UserEmployee;

import jakarta.persistence.*;

//import javax.persistence.*;

import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.GeneratedValue;

@Data
public class ResponseUserempDept {

    private UserEmployee userEmployee;
    private Dept dept;
	public UserEmployee getUserEmployee() {
		return userEmployee;
	}
	public void setUserEmployee(UserEmployee userEmployee) {
		this.userEmployee = userEmployee;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
    
    
    
}
