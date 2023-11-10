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

import com.vti.dto.NhanVienDTO;
import com.vti.entity.NhanVien;
import com.vti.form.nhanvien.CreatingNhanVienForm;
import com.vti.form.nhanvien.NhanVienFilterForm;
import com.vti.form.nhanvien.UpdatingNhanVienForm;
import com.vti.service.INhanVienService;

@RestController
@RequestMapping(value = "api/v1/NhanVien")
public class NhanVienController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private INhanVienService service;

	@GetMapping()
	public Page<NhanVienDTO> getAllNhanViens(
			Pageable pageable, 
			@RequestParam(value = "search", required = false) String search,
			NhanVienFilterForm filterForm) {

		Page<NhanVien> entityPages = service.getAllNhanViens(pageable, search, filterForm);

		// convert entities --> dtos
		List<NhanVienDTO> dtos = modelMapper.map(
				entityPages.getContent(), 
				new TypeToken<List<NhanVienDTO>>() {}.getType());

		Page<NhanVienDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

		return dtoPages;
	}
	
	@GetMapping(value = "/{id}")
	public NhanVienDTO getNhanVienByID(@PathVariable(name = "id") int id) {
		NhanVien entity = service.getNhanVienByID(id);

		// convert entity to dto
		NhanVienDTO dto = modelMapper.map(entity, NhanVienDTO.class);
		
		dto.add(linkTo(methodOn(NhanVienController.class).getNhanVienByID(id)).withSelfRel());

		return dto;
	}

	@PostMapping()
	public void createNhanVien(@RequestBody CreatingNhanVienForm form) {
		service.createNhanVien(form);
	}
	
	@PutMapping(value = "/{id}")
	public void updateNhanVien(
//			@NhanVienIDExists
			@PathVariable(name = "id") int id, 
			@RequestBody UpdatingNhanVienForm form) {
		form.setId(id);
		service.updateNhanVien(form);
	}
	
	@DeleteMapping(value = "/{id}")
	public void deleteNhanVien(@PathVariable(name = "id") int id) {
		service.deleteNhanVien(id);
	}
}

