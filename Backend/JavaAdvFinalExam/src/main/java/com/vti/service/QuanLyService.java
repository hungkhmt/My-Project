package com.vti.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vti.entity.ChiNhanh;
import com.vti.entity.QuanLy;
import com.vti.form.quanly.CreatingQuanLyForm;
import com.vti.form.quanly.QuanLyFilterForm;
import com.vti.form.quanly.UpdatingQuanLyForm;
import com.vti.repository.IChiNhanhRepository;
import com.vti.repository.IQuanLyRepository;
import com.vti.specification.quanly.QuanLySpecification;

@Service
@Transactional
public class QuanLyService implements IQuanLyService {

	@Autowired
	private IQuanLyRepository repository;
	
	@Autowired
	private IChiNhanhRepository chinhanhRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	public Page<QuanLy> getAllQuanLys(
			Pageable pageable, 
			String search, 
			QuanLyFilterForm filterForm) {
		
		Specification<QuanLy> where = QuanLySpecification.buildWhere(search, filterForm);
		return repository.findAll(where, pageable);
	}
	
	public QuanLy getQuanLyByID(int id) {
		return repository.findById(id).get();
	}
	
	@Transactional
	public void createQuanLy(CreatingQuanLyForm form) {
		
		// omit id field
		TypeMap<CreatingQuanLyForm, QuanLy> typeMap = modelMapper.getTypeMap(CreatingQuanLyForm.class, QuanLy.class);
		if (typeMap == null) { // if not already added
			// skip field
			modelMapper.addMappings(new PropertyMap<CreatingQuanLyForm, QuanLy>() {
				@Override
				protected void configure() {
					skip(destination.getId());
				}
			});
		}

		// convert form to entity
		QuanLy QuanLy = modelMapper.map(form, QuanLy.class);
		
		repository.save(QuanLy);
		
		// update totalmember cho department
		Integer chiNhanhID = QuanLy.getChiNhanh().getId();	
		ChiNhanh chinhanh = chinhanhRepository.findById(chiNhanhID).get();
		if(chiNhanhID != null) {
			chinhanh.setSoLuongQuanLy(chinhanh.getSoLuongQuanLy() + 1);
			chinhanhRepository.save(chinhanh);
		}		
	}

	public void updateQuanLy(UpdatingQuanLyForm form) {

		// convert form to entity
		QuanLy quanLy = modelMapper.map(form, QuanLy.class);

		repository.save(quanLy);
	}
	
//	public boolean isQuanLyExistsByUsername(String username) {
//		return repository.existsByUsername(username);
//	}
	
	@Transactional
	public void deleteQuanLy(Integer id) {

		QuanLy QuanLy = getQuanLyByID(id);
		ChiNhanh chinhanh = QuanLy.getChiNhanh();
		if(chinhanh != null) {
			chinhanh.setSoLuongQuanLy(chinhanh.getSoLuongQuanLy() - 1);
			chinhanhRepository.save(chinhanh);
		}
		
		repository.deleteById(id);
		
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		QuanLy quanly = repository.findByUsername(username);
		
		if(quanly == null) {
			throw new UsernameNotFoundException(username);
		}
		return new User(quanly.getUsername(), quanly.getPassword(), AuthorityUtils.createAuthorityList(quanly.getRole().toString()));
	}
	
	public QuanLy getQuanLyByUsername(String username) {
		return repository.findByUsername(username);
	}
}


