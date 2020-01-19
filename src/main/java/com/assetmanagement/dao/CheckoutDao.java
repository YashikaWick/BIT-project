package com.assetmanagement.dao;

import com.assetmanagement.entity.Checkout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckoutDao extends JpaRepository<Checkout, Integer> {

    @Query(value = "SELECT concat('CO',lpad(substring(max(c.checkoutno),3,5)+1,5,'0')) FROM Checkout c",nativeQuery =true )
        //@Query("SELECT substring(max(po.porderno,2,5)FROM Porder po")
    String lastChoutNo();

    @Query("SELECT c FROM Checkout c ORDER BY c.id ASC ")
    Page<Checkout> findAll(Pageable of);

    @Query("SELECT co FROM Checkout co WHERE co.checkoutno= :checkoutno")
    Checkout findByCoNo(@Param("checkoutno") String checkoutno);

}
