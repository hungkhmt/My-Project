package com.vti.form.nhanvien;

import com.vti.entity.NhanVien.GioiTinh;
import com.vti.entity.NhanVien.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatingNhanVienForm {
	private String firstName;
	private String lastName;
	private GioiTinh gioiTinh;
	private Role role;
	private int namSinh;
	private int chiNhanhID;
}
