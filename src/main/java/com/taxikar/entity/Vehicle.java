package com.taxikar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by parinaz on 2/8/2018.
 */
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    private Long id;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "PLATE_NUMBER")
    private String plateNumber;
    @Column(name = "CAR_CARD")
    private String carCard;
    @Column(name = "CAR_IMG")
    private String carImg;
    @Column(name = "DESCRIPTION")
    private String description;

    public Vehicle(){}
    public Vehicle(Long userId, String plateNumber){
        this.userId = userId;
        this.plateNumber = plateNumber;
    }

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public Long getUserId(){return userId;}
    public void setUserId(Long userId){this.userId = userId;}

    public String getPlateNumber(){return plateNumber;}
    public void setPlateNumber(String plateNumber){this.plateNumber = plateNumber;}

    public String getCarCard(){return carCard;}
    public void setCarCard(String carCard){this.carCard = carCard;}

    public String getCarImg(){return carImg;}
    public void setCarImg(String carImg){this.carImg = carImg;}

    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}
}
