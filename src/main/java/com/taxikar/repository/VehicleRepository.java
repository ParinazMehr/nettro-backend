package com.taxikar.repository;

import com.taxikar.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VehicleRepository extends JpaRepository<Vehicle,String>
{
}
