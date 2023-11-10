package com.vti.specification.nhanvien;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.vti.entity.NhanVien;
import com.vti.form.nhanvien.NhanVienFilterForm;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class NhanVienSpecification {

	@SuppressWarnings("deprecation")
	public static Specification<NhanVien> buildWhere(String search, NhanVienFilterForm filterForm) {
		
		Specification<NhanVien> where = null;
		
		if (!StringUtils.isEmpty(search)) {
			search = search.trim();
			CustomSpecification fullName = new CustomSpecification("fullName", search);
			where = Specification.where(fullName);
		}
		
		// if there is filter by min dongia
		if (filterForm != null && filterForm.getMinID() != null) {
			CustomSpecification minId = new CustomSpecification("minSoLuongNhanVien", filterForm.getMinID());
			if (where == null) {
				where = minId;
			} else {
				where = where.and(minId);
			}
		}
		
		// if there is filter by max dongia
		if (filterForm != null && filterForm.getMaxID() != null) {
			CustomSpecification maxId = new CustomSpecification("maxSoLuongNhanVien", filterForm.getMaxID());
			if (where == null) {
				where = maxId;
			} else {
				where = where.and(maxId);
			}
		}

		return where;
	}
}

@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomSpecification implements Specification<NhanVien> {

	@NonNull
	private String field;
	@NonNull
	private Object value;

	@Override
	public Predicate toPredicate(
			Root<NhanVien> root, 
			CriteriaQuery<?> query, 
			CriteriaBuilder criteriaBuilder) {

		if (field.equalsIgnoreCase("fullName")) {
			return criteriaBuilder.like(root.get("fullName"), "%" + value.toString() + "%");
		}
		
		if (field.equalsIgnoreCase("minID")) {
			return criteriaBuilder.greaterThanOrEqualTo(root.get("id"), value.toString());
		}
		
		if (field.equalsIgnoreCase("maxID")) {
			return criteriaBuilder.lessThanOrEqualTo(root.get("id"), value.toString());
		}

		return null;
	}
}

