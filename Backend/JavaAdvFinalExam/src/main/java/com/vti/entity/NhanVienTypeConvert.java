package com.vti.entity;

import javax.persistence.AttributeConverter;

import com.vti.entity.NhanVien.GioiTinh;

public class NhanVienTypeConvert implements AttributeConverter<NhanVien.GioiTinh, String>{

	@Override
	public String convertToDatabaseColumn(GioiTinh attribute) {
		if (attribute == null) {
			return null;
		}

		return attribute.getValue();
	}

	@Override
	public GioiTinh convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}

		return NhanVien.GioiTinh.toEnum(dbData);
	}

}
