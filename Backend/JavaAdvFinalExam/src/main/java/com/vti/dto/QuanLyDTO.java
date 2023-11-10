package com.vti.dto;

import org.springframework.hateoas.RepresentationModel;

import com.vti.entity.QuanLy.GioiTinh;
import com.vti.entity.QuanLy.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuanLyDTO extends RepresentationModel<ChiNhanhDTO>{
	private int id;
	
	private String username;

	private String fullName;
	
	private GioiTinh gioiTinh;
	
	private int namSinh;
	
	private Role role;

	private int chinhanhId;
}
