package com.vti.form.chinhanh;


import java.util.List;

import com.vti.entity.QuanLy.GioiTinh;
import com.vti.entity.QuanLy.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatingChiNhanhForm {
	private String diaChi;
	private int soLuongNhanVien;
	private int soLuongQuanLy;
	
	private List<NhanVien> nhanVien;
	@Data
	@NoArgsConstructor
	public static class NhanVien {
		
//		@NotBlank(message = "The name mustn't be null value")
//		@Length(max = 50, message = "The name's length is max 50 characters")
//		@AccountUsernameNotExists
		private String firstName;
		private String lastName;
		private GioiTinh gioiTinh;
		private Role role;
		private int namSinh;
	}
	
	private List<QuanLy> quanLy;
	@Data
	@NoArgsConstructor
	public static class QuanLy {
		
//		@NotBlank(message = "The name mustn't be null value")
//		@Length(max = 50, message = "The name's length is max 50 characters")
//		@AccountUsernameNotExists
		private String firstName;
		private String lastName;
		private GioiTinh gioiTinh;
		private int namSinh;
		private String username;
		private String password;
		private Role role;
	}
}
