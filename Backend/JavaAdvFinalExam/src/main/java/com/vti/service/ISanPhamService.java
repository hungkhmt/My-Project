package com.vti.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vti.entity.SanPham;
import com.vti.form.sanpham.CreatingSanPhamForm;
import com.vti.form.sanpham.SanPhamFilterForm;
import com.vti.form.sanpham.UpdatingSanPhamForm;

public interface ISanPhamService {
	public Page<SanPham> getAllSanPhams(Pageable pageable, String search, SanPhamFilterForm filterForm);

	public SanPham getSanPhamByID(int id);

	public void createSanPham(CreatingSanPhamForm form);

	public void updateSanPham(UpdatingSanPhamForm form);

	public boolean isSanPhamExistsByID(Integer id);
	
	public void deleteSanPham(Integer id);
	
	public void deleteSanPhams(List<Integer> idList);
}
