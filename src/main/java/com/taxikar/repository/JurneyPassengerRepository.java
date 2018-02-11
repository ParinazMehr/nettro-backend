package com.taxikar.repository;

import com.taxikar.entity.JurneyPassenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by parinaz on 2/10/2018.
 */
public interface JurneyPassengerRepository extends JpaRepository<JurneyPassenger, Long> {
    @Query("select jp from JurneyPassenger jp where jp.id=?1")
    JurneyPassenger getOneWithId(String id);

    @Query("select jp from JurneyPassenger jp where jp.userId=?1 and jp.startTime>=?2")
    List<JurneyPassenger> getAllActive(String id, Timestamp time);

    @Query("select jp from JurneyPassenger jp where jp.userId = ?1 and jp.startTime<?2")
    List<JurneyPassenger> getAllPast(String id, Timestamp time);
}
