package com.taxikar.repository;

import com.taxikar.entity.JurneyDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by parinaz on 2/9/2018.
 */
public interface JurneyDriverRepository extends JpaRepository<JurneyDriver, Long> {
    @Query("select jr from jurney_driver jr where jr.price=?1")
    JurneyDriver getSpecificPrice(Long price);
}
