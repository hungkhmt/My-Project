package com.vti.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "QuanLy")
@Data
@NoArgsConstructor
public class QuanLy {
	private static final long serialVersionUID = 1L;

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "firstName", length = 50, nullable = false)
	private String firstName;

	@Column(name = "lastName", length = 50, nullable = false)
	private String lastName;

	@Formula(" concat(firstName, ' ', lastName) ")
	private String fullName;	

	@Column(name = "gioiTinh")
	@Convert(converter = QuanLyConvert.class)
	private GioiTinh gioiTinh;
	
	@Column(name = "namSinh")
	private int namSinh;
	
	@ManyToOne
	@JoinColumn(name = "chiNhanhID", nullable = false)
	private ChiNhanh chiNhanh;

	@Column(name = "username", length = 50, nullable = false, unique = true)
	private String username;

	@Column(name = "password", length = 800, nullable = false)
	private String password;
	
	@Column(name = "`role`")
	@Enumerated(EnumType.STRING)
	private Role role;

	public enum Role {
		ADMIN, QL1, QL2, QL3;
	}
	
	public enum GioiTinh {
		NAM("Nam"), NỮ("Nữ"), KHÁC("Khác");
		private String value;

		private GioiTinh(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static GioiTinh toEnum(String sqlValue) {
			for (GioiTinh type : GioiTinh.values()) {
				if (type.getValue().equals(sqlValue)) {
					return type;
				}
			}
			return null;
		}
	}
}
