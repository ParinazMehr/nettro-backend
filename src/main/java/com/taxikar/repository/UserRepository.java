package com.taxikar.repository;

import com.taxikar.entity.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users,String>
{

}