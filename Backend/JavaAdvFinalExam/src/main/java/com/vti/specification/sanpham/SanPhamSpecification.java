package com.vti.specification.sanpham;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.vti.entity.SanPham;
import com.vti.form.sanpham.SanPhamFilterForm;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class SanPhamSpecification {

	@SuppressWarnings("deprecation")
	public static Specification<SanPham> buildWhere(String search, SanPhamFilterForm filterForm) {
		
		Specification<SanPham> where = null;
		
		if (!StringUtils.isEmpty(search)) {
			search = search.trim();
			CustomSpecification tenSP = new CustomSpecification("tenSP", search);
			where = Specification.where(tenSP);
		}
		
		// if there is filter by min dongia
		if (filterForm != null && filterForm.getMinDonGia() != null) {
			CustomSpecification minId = new CustomSpecification("minDonGia", filterForm.getMinDonGia());
			if (where == null) {
				where = minId;
			} else {
				where = where.and(minId);
			}
		}
		
		// if there is filter by max dongia
		if (filterForm != null && filterForm.getMaxDonGia() != null) {
			CustomSpecification maxId = new CustomSpecification("maxDonGia", filterForm.getMaxDonGia());
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
class CustomSpecification implements Specification<SanPham> {

	@NonNull
	private String field;
	@NonNull
	private Object value;

	@Override
	public Predicate toPredicate(
			Root<SanPham> root, 
			CriteriaQuery<?> query, 
			CriteriaBuilder criteriaBuilder) {

		if (field.equalsIgnoreCase("tenSP")) {
			return criteriaBuilder.like(root.get("tenSP"), "%" + value.toString() + "%");
		}
		
		if (field.equalsIgnoreCase("minDonGia")) {
			return criteriaBuilder.greaterThanOrEqualTo(root.get("donGia"), value.toString());
		}
		
		if (field.equalsIgnoreCase("maxDonGia")) {
			return criteriaBuilder.lessThanOrEqualTo(root.get("donGia"), value.toString());
		}

		return null;
	}
}

