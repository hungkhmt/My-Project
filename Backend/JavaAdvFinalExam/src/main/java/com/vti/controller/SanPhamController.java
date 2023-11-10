package com.vti.controller;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.SanPhamDTO;
import com.vti.entity.SanPham;
import com.vti.form.sanpham.CreatingSanPhamForm;
import com.vti.form.sanpham.SanPhamFilterForm;
import com.vti.form.sanpham.UpdatingSanPhamForm;
import com.vti.service.ISanPhamService;

@RestController
@RequestMapping(value = "api/v1/SanPham")
@Validated
public class SanPhamController {
	@Autowired
	private ISanPhamService service;

	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping()
	public Page<SanPhamDTO> getAllSanPhams(
			Pageable pageable, 
			@RequestParam(value = "search", required = false) String search,
			SanPhamFilterForm filterForm) {

		Page<SanPham> entityPages = service.getAllSanPhams(pageable, search, filterForm);

		// convert entities --> dtos
		List<SanPhamDTO> dtos = modelMapper.map(
				entityPages.getContent(), 
				new TypeToken<List<SanPhamDTO>>() {}.getType());

		Page<SanPhamDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

		return dtoPages;
	}
	
	@GetMapping(value = "/{id}")
	public SanPhamDTO getSanPhamByID(@PathVariable(name = "id") int id) {
		SanPham entity = service.getSanPhamByID(id);
		SanPhamDTO dto =  modelMapper.map(entity, SanPhamDTO.class);
		
		dto.add(linkTo(methodOn(SanPhamController.class).getSanPhamByID(id)).withSelfRel());

		return dto;
	}
	
	@PostMapping()
	public void createSanPham(@RequestBody @Valid CreatingSanPhamForm form) {
		service.createSanPham(form);
	}
	
	@PutMapping(value = "/{id}")
	public void updateSanPham(
			@PathVariable(name = "id") int id, 
			@RequestBody UpdatingSanPhamForm form) {
		form.setId(id);
		service.updateSanPham(form);
	}
	
	@DeleteMapping(value = "/{id}")
	public void deleteSanPham(@PathVariable(name = "id") int id) {
		service.deleteSanPham(id);
	}
	
	@DeleteMapping()
	public void deleteSanPhams(@RequestParam(name = "idList") List<Integer> idList) {
		service.deleteSanPhams(idList);
	}
}
