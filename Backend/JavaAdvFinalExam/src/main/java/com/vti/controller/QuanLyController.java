package com.vti.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.QuanLyDTO;
import com.vti.entity.QuanLy;
import com.vti.form.quanly.CreatingQuanLyForm;
import com.vti.form.quanly.QuanLyFilterForm;
import com.vti.form.quanly.UpdatingQuanLyForm;
import com.vti.service.IQuanLyService;

@RestController
@RequestMapping(value = "api/v1/QuanLy")
public class QuanLyController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IQuanLyService service;

	@GetMapping()
	public Page<QuanLyDTO> getAllQuanLys(
			Pageable pageable, 
			@RequestParam(value = "search", required = false) String search,
			QuanLyFilterForm filterForm) {

		Page<QuanLy> entityPages = service.getAllQuanLys(pageable, search, filterForm);

		// convert entities --> dtos
		List<QuanLyDTO> dtos = modelMapper.map(
				entityPages.getContent(), 
				new TypeToken<List<QuanLyDTO>>() {}.getType());

		Page<QuanLyDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

		return dtoPages;
	}
	
	@GetMapping(value = "/{id}")
	public QuanLyDTO getQuanLyByID(@PathVariable(name = "id") int id) {
		QuanLy entity = service.getQuanLyByID(id);

		// convert entity to dto
		QuanLyDTO dto = modelMapper.map(entity, QuanLyDTO.class);
		
		
		dto.add(linkTo(methodOn(QuanLyController.class).getQuanLyByID(id)).withSelfRel());

		return dto;
	}
	
	@GetMapping(value = "/user/{username}")
	public QuanLyDTO getQuanLyByUsername(@PathVariable(name = "username") String username) {
		QuanLy entity = service.getQuanLyByUsername(username);

		// convert entity to dto
		QuanLyDTO dto = modelMapper.map(entity, QuanLyDTO.class);
		
		
		dto.add(linkTo(methodOn(QuanLyController.class).getQuanLyByUsername(username)).withSelfRel());

		return dto;
	}

	@PostMapping()
	public void createQuanLy(@RequestBody CreatingQuanLyForm form) {
		service.createQuanLy(form);
	}
	
	@PutMapping(value = "/{id}")
	public void updateQuanLy(
//			@QuanLyIDExists
			@PathVariable(name = "id") int id, 
			@RequestBody UpdatingQuanLyForm form) {
		form.setId(id);
		service.updateQuanLy(form);
	}
	
	@DeleteMapping(value = "/{id}")
	public void deleteQuanLy(@PathVariable(name = "id") int id) {
		service.deleteQuanLy(id);
	}
}

