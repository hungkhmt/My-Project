package com.vti.service;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vti.entity.ChiNhanh;
import com.vti.entity.NhanVien;
import com.vti.entity.QuanLy;
import com.vti.form.chinhanh.ChiNhanhFilterForm;
import com.vti.form.chinhanh.CreatingChiNhanhForm;
import com.vti.form.chinhanh.UpdatingChiNhanhForm;
import com.vti.repository.IChiNhanhRepository;
import com.vti.repository.INhanVienRepository;
import com.vti.repository.IQuanLyRepository;
import com.vti.specification.chinhanh.ChiNhanhSpecification;

@Service
public class ChiNhanhService implements IChiNhanhService{
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IChiNhanhRepository repository;
	
	@Autowired
	private INhanVienRepository nhanVienRepository;
	
	@Autowired
	private IQuanLyRepository quanLyRepository;
	
	public Page<ChiNhanh> getAllChiNhanhs(Pageable pageable, String search, ChiNhanhFilterForm filterForm) {

		Specification<ChiNhanh> where = ChiNhanhSpecification.buildWhere(search, filterForm);
		return repository.findAll(where, pageable);
	}

	public ChiNhanh getChiNhanhByID(int id) {
		return repository.findById(id).get();
	}

	@Transactional
	public void createChiNhanh(CreatingChiNhanhForm form) {

		// convert form to entity
		ChiNhanh chiNhanhEntity = modelMapper.map(form, ChiNhanh.class);

		// create ChiNhanh
		ChiNhanh chiNhanh = repository.save(chiNhanhEntity);
		
		// create Nhanvien
		List<NhanVien> accountEntities = chiNhanhEntity.getNhanVien();
		for (NhanVien NhanVien : accountEntities) {
			NhanVien.setChiNhanh(chiNhanh);
		}
		nhanVienRepository.saveAll(accountEntities);
		
		List<QuanLy> quanLyEntities = chiNhanhEntity.getQuanLy();
		for (QuanLy QuanLy : quanLyEntities) {
			QuanLy.setChiNhanh(chiNhanh);
		}
		quanLyRepository.saveAll(quanLyEntities);
	}

	public void updateChiNhanh(UpdatingChiNhanhForm form) {

		// convert form to entity
		ChiNhanh ChiNhanh = modelMapper.map(form, ChiNhanh.class);

		repository.save(ChiNhanh);
	}

	public boolean isChiNhanhExistsByID(Integer id) {
		return repository.existsById(id);
	}
	
	public void deleteChiNhanh(Integer id) {
			
		repository.deleteById(id);
	}
}
