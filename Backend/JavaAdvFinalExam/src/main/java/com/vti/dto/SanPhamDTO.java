package com.vti.dto;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SanPhamDTO extends RepresentationModel<SanPhamDTO>{
	private Integer id;
	private String tenSP;
	private int donGia;
	private int soLuong;
	private Date ngayNhap;
	private Date ngayHetHan;
}
