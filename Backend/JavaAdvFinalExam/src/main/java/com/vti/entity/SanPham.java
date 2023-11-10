package com.vti.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Entity
@Table(name = "SanPham")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SanPham {
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "tenSP", length = 50, nullable = false, unique = true)
	@NonNull
	private String tenSP;

	@Column(name = "donGia", columnDefinition = "int unsigned", nullable = false)
	private int donGia;

	@Column(name = "soLuong", columnDefinition = "int unsigned", nullable = false)
	private int soLuong;
	
	@Column(name = "ngayNhap", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date ngayNhap;
	
	@Column(name = "ngayHetHan", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date ngayHetHan;
}
