package com.taxikar.repository;

import com.taxikar.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<Users,String>
{
    @Query("select user from Users user where user.mobileNumber=?1")
    Users FindUserByMobileNumber(String MobileNumber);

    @Query("select case when count(u)>0 then true else false end from Users u where u.id=?1")
    boolean ExistanceByUserId(String userId);
}
