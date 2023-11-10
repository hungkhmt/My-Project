package com.vti.specification.quanly;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.vti.entity.QuanLy;
import com.vti.form.quanly.QuanLyFilterForm;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class QuanLySpecification {

	@SuppressWarnings("deprecation")
	public static Specification<QuanLy> buildWhere(String search, QuanLyFilterForm filterForm) {
		
		Specification<QuanLy> where = null;
		
		if (!StringUtils.isEmpty(search)) {
			search = search.trim();
			CustomSpecification username = new CustomSpecification("username", search);
			where = Specification.where(username);
		}
		
		// if there is filter by min dongia
		if (filterForm != null && filterForm.getMinID() != null) {
			CustomSpecification minId = new CustomSpecification("minSoLuongQuanLy", filterForm.getMinID());
			if (where == null) {
				where = minId;
			} else {
				where = where.and(minId);
			}
		}
		
		// if there is filter by max dongia
		if (filterForm != null && filterForm.getMaxID() != null) {
			CustomSpecification maxId = new CustomSpecification("maxSoLuongQuanLy", filterForm.getMaxID());
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
class CustomSpecification implements Specification<QuanLy> {

	@NonNull
	private String field;
	@NonNull
	private Object value;

	@Override
	public Predicate toPredicate(
			Root<QuanLy> root, 
			CriteriaQuery<?> query, 
			CriteriaBuilder criteriaBuilder) {

		if (field.equalsIgnoreCase("username")) {
			return criteriaBuilder.like(root.get("username"), "%" + value.toString() + "%");
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

