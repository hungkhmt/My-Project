package com.vti.controller;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.LoginInfoDto;
import com.vti.entity.QuanLy;
import com.vti.service.IQuanLyService;

@RestController
@RequestMapping(value = "api/v1/login")
@CrossOrigin("*")
public class LoginController {
	
	@Autowired
	private IQuanLyService service;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping()
	public ResponseEntity<?> login(Principal principal) {
		String username = principal.getName();
		QuanLy entity = service.getQuanLyByUsername(username);
		
		LoginInfoDto dto = modelMapper.map(entity, LoginInfoDto.class);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
}
