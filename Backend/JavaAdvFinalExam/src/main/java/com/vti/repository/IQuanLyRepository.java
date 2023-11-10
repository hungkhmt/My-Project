package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vti.entity.QuanLy;

public interface IQuanLyRepository extends JpaRepository<QuanLy, Integer>, JpaSpecificationExecutor<QuanLy>{
	public QuanLy findByUsername(String username);
	
	//boolean existsByUsername(String username);
}
