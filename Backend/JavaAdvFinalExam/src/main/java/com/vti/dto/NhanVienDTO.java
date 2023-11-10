package com.vti.dto;

import org.springframework.hateoas.RepresentationModel;

import com.vti.entity.NhanVien.GioiTinh;
import com.vti.entity.NhanVien.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NhanVienDTO extends RepresentationModel<ChiNhanhDTO> {
	private int id;

	private String fullName;
	
	private GioiTinh gioiTinh;
	
	private int namSinh;
	
	private Role role;

	private int chiNhanhID;
}
