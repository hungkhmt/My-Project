package com.vti.form.sanpham;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatingSanPhamForm {
	private int id;
	private String tenSP;
	private int donGia;
	private int soLuong;
	private Date ngayNhap;
	private Date ngayHetHan;
}
