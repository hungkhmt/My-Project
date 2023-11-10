package com.vti.specification.chinhanh;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.vti.entity.ChiNhanh;
import com.vti.form.chinhanh.ChiNhanhFilterForm;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class ChiNhanhSpecification {

	@SuppressWarnings("deprecation")
	public static Specification<ChiNhanh> buildWhere(String search, ChiNhanhFilterForm filterForm) {
		
		Specification<ChiNhanh> where = null;
		
		if (!StringUtils.isEmpty(search)) {
			search = search.trim();
			CustomSpecification diaChi = new CustomSpecification("diaChi", search);
			where = Specification.where(diaChi);
		}
		
		// if there is filter by min dongia
		if (filterForm != null && filterForm.getMinSoLuongNhanVien() != null) {
			CustomSpecification minId = new CustomSpecification("minSoLuongNhanVien", filterForm.getMinSoLuongNhanVien());
			if (where == null) {
				where = minId;
			} else {
				where = where.and(minId);
			}
		}
		
		// if there is filter by max dongia
		if (filterForm != null && filterForm.getMaxSoLuongNhanVien() != null) {
			CustomSpecification maxId = new CustomSpecification("maxSoLuongNhanVien", filterForm.getMaxSoLuongNhanVien());
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
class CustomSpecification implements Specification<ChiNhanh> {

	@NonNull
	private String field;
	@NonNull
	private Object value;

	@Override
	public Predicate toPredicate(
			Root<ChiNhanh> root, 
			CriteriaQuery<?> query, 
			CriteriaBuilder criteriaBuilder) {

		if (field.equalsIgnoreCase("diaChi")) {
			return criteriaBuilder.like(root.get("diaChi"), "%" + value.toString() + "%");
		}
		
		if (field.equalsIgnoreCase("minSoLuongNhanVien")) {
			return criteriaBuilder.greaterThanOrEqualTo(root.get("soLuongNhanVien"), value.toString());
		}
		
		if (field.equalsIgnoreCase("maxSoLuongNhanVien")) {
			return criteriaBuilder.lessThanOrEqualTo(root.get("soLuongNhanVien"), value.toString());
		}

		return null;
	}
}

