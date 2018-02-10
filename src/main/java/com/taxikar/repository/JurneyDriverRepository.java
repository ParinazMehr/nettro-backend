package com.taxikar.repository;

import com.taxikar.entity.JurneyDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by parinaz on 2/9/2018.
 */
public interface JurneyDriverRepository extends JpaRepository<JurneyDriver, Long> {
    @Query("select jr from jurney_driver jr where jr.userId=?1 and jr.startTime=?2")
    List<JurneyDriver> getSpecificPrice(String userId, Timestamp start);

    @Query("select jr from jurney_driver jr where jr.id=?1")
    JurneyDriver getOneJourney(String id);
}
