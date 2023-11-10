package com.vti.dto;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChiNhanhDTO extends RepresentationModel<ChiNhanhDTO>{
	private int id;

	private String diaChi;

	private int soLuongNhanVien;

	private int soLuongQuanLy;

	private List<NhanVienDTO> nhanVien;
	
	private List<QuanLyDTO> quanLy;

	@Data
	@NoArgsConstructor
	public static class NhanVienDTO extends RepresentationModel<ChiNhanhDTO> {

		@JsonProperty("nhanVienID")
		private int id;
		
		private String fullName;
	}
	
	@Data
	@NoArgsConstructor
	public static class QuanLyDTO extends RepresentationModel<ChiNhanhDTO> {

		@JsonProperty("quanLyID")
		private int id;
		
		private String fullName;
	}
}
