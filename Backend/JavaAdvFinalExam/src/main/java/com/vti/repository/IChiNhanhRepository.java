package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vti.entity.ChiNhanh;

public interface IChiNhanhRepository extends JpaRepository<ChiNhanh, Integer>, JpaSpecificationExecutor<ChiNhanh>
{

}
