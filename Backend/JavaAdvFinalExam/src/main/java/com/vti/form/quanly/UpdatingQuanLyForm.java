package com.vti.form.quanly;

import com.vti.entity.QuanLy.GioiTinh;
import com.vti.entity.QuanLy.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatingQuanLyForm {
	private int id;
	private String firstName;
	private String lastName;
	private GioiTinh gioiTinh;
	private int namSinh;
	private String username;
	private String password;
	private Role role;
}
