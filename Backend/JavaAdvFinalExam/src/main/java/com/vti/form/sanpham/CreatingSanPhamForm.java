package com.vti.form.sanpham;

import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatingSanPhamForm {
	@NotBlank(message = "{SanPham.createSanPham.form.name.NotBlank}")
	@Length(min=4, max=50, message = "{SanPham.createSanPham.form.name.LengthRange}")
	private String tenSP;
	
	@Digits(integer = 5, fraction = 0, message = "{SanPham.createSanPham.form.soluong.Digits}")
	@NotBlank(message = "{SanPham.createSanPham.form.name.NotBlank}")
	private int donGia;
	
	@Digits(integer = 5, fraction = 0, message = "{SanPham.createSanPham.form.soluong.Digits}")
	@NotBlank(message = "{SanPham.createSanPham.form.name.NotBlank}")
	private int soLuong;
	
	@PastOrPresent(message = "{SanPham.createSanPham.form.ngay.PastOrPresent}")
	@NotBlank(message = "{SanPham.createSanPham.form.name.NotBlank}")
	private Date ngayNhap;
	
	@FutureOrPresent(message = "{SanPham.createSanPham.form.ngay.FutureOrPresent}")
	@NotBlank(message = "{SanPham.createSanPham.form.name.NotBlank}")
	private Date ngayHetHan;
}
