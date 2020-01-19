package com.assetmanagement.dao;

import com.assetmanagement.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModelDao extends JpaRepository<Model, Integer> {

    @Query(value="SELECT new Model(m.id,m.name) FROM Model m ")
    List<Model> list();

    @Query(value="SELECT new Model(m.id,m.name,m.brandId) FROM Model m where m.brandId.id=:brandId ")
    List<Model> listByBrand(@Param("brandId")Integer brandId);
}
