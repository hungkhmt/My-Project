package com.vti.service;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vti.entity.SanPham;
import com.vti.form.sanpham.CreatingSanPhamForm;
import com.vti.form.sanpham.SanPhamFilterForm;
import com.vti.form.sanpham.UpdatingSanPhamForm;
import com.vti.repository.ISanPhamRepository;
import com.vti.specification.sanpham.SanPhamSpecification;

@Service
public class SanPhamService implements ISanPhamService{
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ISanPhamRepository repository;
	
	
	public Page<SanPham> getAllSanPhams(Pageable pageable, String search, SanPhamFilterForm filterForm) {

		Specification<SanPham> where = SanPhamSpecification.buildWhere(search, filterForm);
		return repository.findAll(where, pageable);
	}

	public SanPham getSanPhamByID(int id) {
		return repository.findById(id).get();
	}

	public void createSanPham(CreatingSanPhamForm form) {

		// omit id field
		TypeMap<CreatingSanPhamForm, SanPham> typeMap = modelMapper.getTypeMap(CreatingSanPhamForm.class, SanPham.class);
		if (typeMap == null) { // if not already added
		// skip field
			modelMapper.addMappings(new PropertyMap<CreatingSanPhamForm, SanPham>() {
				@Override
				protected void configure() {
					skip(destination.getId());
				}
			});
		}

		// convert form to entity
		SanPham SanPham = modelMapper.map(form, SanPham.class);
				
		repository.save(SanPham);
	}

	public void updateSanPham(UpdatingSanPhamForm form) {

		// convert form to entity
		SanPham SanPham = modelMapper.map(form, SanPham.class);

		repository.save(SanPham);
	}
	
	public void deleteSanPham(Integer id) {	
		repository.deleteById(id);
	}

	public boolean isSanPhamExistsByID(Integer id) {
		return repository.existsById(id);
	}

	@Override
	public void deleteSanPhams(List<Integer> idList) {
		repository.deleteAllById(idList);
	}
}
