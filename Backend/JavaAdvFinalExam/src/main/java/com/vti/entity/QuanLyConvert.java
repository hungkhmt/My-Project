package com.vti.entity;

import javax.persistence.AttributeConverter;

import com.vti.entity.QuanLy.GioiTinh;

public class QuanLyConvert implements AttributeConverter<QuanLy.GioiTinh, String>{

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

		return QuanLy.GioiTinh.toEnum(dbData);
	}

}
