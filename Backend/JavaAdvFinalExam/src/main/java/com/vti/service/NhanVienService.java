package com.vti.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vti.entity.ChiNhanh;
import com.vti.entity.NhanVien;
import com.vti.form.nhanvien.CreatingNhanVienForm;
import com.vti.form.nhanvien.NhanVienFilterForm;
import com.vti.form.nhanvien.UpdatingNhanVienForm;
import com.vti.repository.IChiNhanhRepository;
import com.vti.repository.INhanVienRepository;
import com.vti.specification.nhanvien.NhanVienSpecification;

@Service
@Transactional
public class NhanVienService implements INhanVienService{
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private INhanVienRepository repository;
	
	@Autowired
	private IChiNhanhRepository chinhanhRepository;
	
	public Page<NhanVien> getAllNhanViens(Pageable pageable, String search, NhanVienFilterForm filterForm) {

		Specification<NhanVien> where = NhanVienSpecification.buildWhere(search, filterForm);
		return repository.findAll(where, pageable);
	}

	public NhanVien getNhanVienByID(int id) {
		return repository.findById(id).get();
	}

	@Transactional
	public void createNhanVien(CreatingNhanVienForm form) {
		
		// omit id field
		TypeMap<CreatingNhanVienForm, NhanVien> typeMap = modelMapper.getTypeMap(CreatingNhanVienForm.class, NhanVien.class);
		if (typeMap == null) { // if not already added
			// skip field
			modelMapper.addMappings(new PropertyMap<CreatingNhanVienForm, NhanVien>() {
				@Override
				protected void configure() {
					skip(destination.getId());
				}
			});
		}

		// convert form to entity
		NhanVien NhanVien = modelMapper.map(form, NhanVien.class);
		
		repository.save(NhanVien);
		
		// update totalmember cho department
		Integer chiNhanhID = NhanVien.getChiNhanh().getId();		
		ChiNhanh chinhanh = chinhanhRepository.findById(chiNhanhID).get();
		chinhanh.setSoLuongNhanVien(chinhanh.getSoLuongNhanVien() + 1);
		chinhanhRepository.save(chinhanh);
	}

	@Transactional
	public void updateNhanVien(UpdatingNhanVienForm form) {

		// convert form to entity
		NhanVien NhanVien = modelMapper.map(form, NhanVien.class);
		
		NhanVien nv = repository.findById(NhanVien.getId()).get();
		
		Integer beforChiNhanhId = nv.getChiNhanh().getId();
		Integer afterChiNhanhId = form.getChinhanhID();

		repository.save(NhanVien);
		
		if(beforChiNhanhId != afterChiNhanhId) {
			// update totalmember cho befor department	
			ChiNhanh cnBefore = chinhanhRepository.findById(beforChiNhanhId).get();
			cnBefore.setSoLuongNhanVien(cnBefore.getSoLuongNhanVien() - 1);
			chinhanhRepository.save(cnBefore);
			
			// update totalmember cho befor department	
			ChiNhanh cnAfter = chinhanhRepository.findById(afterChiNhanhId).get();
			cnAfter.setSoLuongNhanVien(cnAfter.getSoLuongNhanVien() + 1);
			chinhanhRepository.save(cnAfter);
		}
	}

	public boolean isNhanVienExistsByID(Integer id) {
		return repository.existsById(id);
	}
	
	@Transactional
	public void deleteNhanVien(Integer id) {

		NhanVien NhanVien = getNhanVienByID(id);
		ChiNhanh chinhanh = NhanVien.getChiNhanh();
		if(chinhanh != null) {
			chinhanh.setSoLuongNhanVien(chinhanh.getSoLuongNhanVien() - 1);
			chinhanhRepository.save(chinhanh);
		}
		
		repository.deleteById(id);
		
		
	}
}
