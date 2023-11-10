package com.vti.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "ChiNhanh")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ChiNhanh {
	private static final long serialVersionUID = 1L;

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "diaChi", length = 100, nullable = false, unique = true)
	@NonNull
	private String diaChi;

	@Column(name = "soLuongNhanVien")
	private int soLuongNhanVien;

	@Column(name = "soLuongQuanLy")
	private int soLuongQuanLy;
	
	@OneToMany(mappedBy = "chiNhanh")
	private List<NhanVien> nhanVien;
	
	@OneToMany(mappedBy = "chiNhanh")
	private List<QuanLy> quanLy;
}
