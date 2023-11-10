package com.vti.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vti.entity.ChiNhanh;
import com.vti.form.chinhanh.ChiNhanhFilterForm;
import com.vti.form.chinhanh.CreatingChiNhanhForm;
import com.vti.form.chinhanh.UpdatingChiNhanhForm;

public interface IChiNhanhService{
	public Page<ChiNhanh> getAllChiNhanhs(Pageable pageable, String search, ChiNhanhFilterForm filterForm);

	public ChiNhanh getChiNhanhByID(int id);

	public void createChiNhanh(CreatingChiNhanhForm form);

	public void updateChiNhanh(UpdatingChiNhanhForm form);

	public boolean isChiNhanhExistsByID(Integer id);
	
	public void deleteChiNhanh(Integer id);
}
