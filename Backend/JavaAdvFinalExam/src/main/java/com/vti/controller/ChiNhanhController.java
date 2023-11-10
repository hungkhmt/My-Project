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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.ChiNhanhDTO;
import com.vti.entity.ChiNhanh;
import com.vti.form.chinhanh.ChiNhanhFilterForm;
import com.vti.form.chinhanh.CreatingChiNhanhForm;
import com.vti.form.chinhanh.UpdatingChiNhanhForm;
import com.vti.service.IChiNhanhService;

@RestController
@RequestMapping(value = "api/v1/ChiNhanh")
@Validated
public class ChiNhanhController {

	@Autowired
	private IChiNhanhService service;

	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping()
	public Page<ChiNhanhDTO> getAllChiNhanhs(
			Pageable pageable, 
			@RequestParam(name = "search", required = false) String search,
			ChiNhanhFilterForm filterForm) {
		Page<ChiNhanh> entityPages = service.getAllChiNhanhs(pageable, search, filterForm);

		// convert entities --> dtos
		List<ChiNhanhDTO> dtos = modelMapper.map(
				entityPages.getContent(), 
				new TypeToken<List<ChiNhanhDTO>>() {}.getType());
		
		// add HATEOAS
		for (ChiNhanhDTO dto : dtos) {
			for (ChiNhanhDTO.NhanVienDTO nhanvienDTO : dto.getNhanVien()) {
				nhanvienDTO.add(linkTo(methodOn(NhanVienController.class).getNhanVienByID(nhanvienDTO.getId())).withSelfRel());
			}
			dto.add(linkTo(methodOn(ChiNhanhController.class).getChiNhanhByID(dto.getId())).withSelfRel());
		}
	
		Page<ChiNhanhDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

		return dtoPages;
	}
	
	@GetMapping(value = "/{id}")
	public ChiNhanhDTO getChiNhanhByID(@PathVariable(name = "id") int id) {
		ChiNhanh entity = service.getChiNhanhByID(id);

		// convert entity to dto
		ChiNhanhDTO dto = modelMapper.map(entity, ChiNhanhDTO.class);
		
		dto.add(linkTo(methodOn(ChiNhanhController.class).getChiNhanhByID(id)).withSelfRel());

		return dto;
	}

	@PostMapping()
	public void createChiNhanh(@RequestBody @Valid CreatingChiNhanhForm form) {
		service.createChiNhanh(form);
	}
	
	@PutMapping(value = "/{id}")
	public void updateChiNhanh(
//			@ChiNhanhIDExists 
			@PathVariable(name = "id") int id, 
			@RequestBody UpdatingChiNhanhForm form) {
		form.setId(id);
		service.updateChiNhanh(form);
	}
//
//	@GetMapping("/messages")
//	public String testMessages(@RequestParam(value = "key") String key){
//		return messageSource.getMessage(
//				key, 
//				null, 
//				"Default message", 
//				LocaleContextHolder.getLocale());
//	}
//	
//	@GetMapping("/messages/vi")
//	public String testMessagesVi(@RequestParam(value = "key") String key){
//		return messageSource.getMessage(
//				key, 
//				null, 
//				"Default message", 
//				new Locale("vi", "VN"));
//	}
//	
//	@GetMapping("/messages/en")
//	public String testMessagesOther(@RequestParam(value = "key") String key){
//		return messageSource.getMessage(
//				key, 
//				null, 
//				"Default message", 
//				Locale.US);
//	}
}


