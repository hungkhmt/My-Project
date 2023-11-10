package com.vti.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vti.entity.NhanVien;
import com.vti.form.nhanvien.CreatingNhanVienForm;
import com.vti.form.nhanvien.NhanVienFilterForm;
import com.vti.form.nhanvien.UpdatingNhanVienForm;

public interface INhanVienService {
	public Page<NhanVien> getAllNhanViens(Pageable pageable, String search, NhanVienFilterForm filterForm);

	public NhanVien getNhanVienByID(int id);

	public void createNhanVien(CreatingNhanVienForm form);

	public void updateNhanVien(UpdatingNhanVienForm form);

	public boolean isNhanVienExistsByID(Integer id);
	
	public void deleteNhanVien(Integer id);
}
