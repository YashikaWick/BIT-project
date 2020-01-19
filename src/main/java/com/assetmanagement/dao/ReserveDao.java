package com.assetmanagement.dao;

import com.assetmanagement.entity.Reserve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveDao extends JpaRepository<Reserve, Integer> {

}
