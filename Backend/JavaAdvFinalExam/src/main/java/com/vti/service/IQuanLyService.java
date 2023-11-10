package com.vti.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.vti.entity.QuanLy;
import com.vti.form.quanly.CreatingQuanLyForm;
import com.vti.form.quanly.QuanLyFilterForm;
import com.vti.form.quanly.UpdatingQuanLyForm;

public interface IQuanLyService extends UserDetailsService{

	public Page<QuanLy> getAllQuanLys(Pageable pageable, String search, QuanLyFilterForm filterForm);

	public QuanLy getQuanLyByID(int id);
	
	public void createQuanLy(CreatingQuanLyForm form);
	
	public void updateQuanLy(UpdatingQuanLyForm form);
	
//	public boolean isQuanLyExistsByUsername(String username);
	
	public void deleteQuanLy(Integer id);
	
	public QuanLy getQuanLyByUsername(String username);
}
