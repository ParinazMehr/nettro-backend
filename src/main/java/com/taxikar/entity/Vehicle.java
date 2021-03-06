package com.taxikar.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by parinaz on 2/8/2018.
 */
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(columnDefinition = "char(36)")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "PLATE_NUMBER")
    private String plateNumber;
    @Column(name = "CAR_CARD")
    private String carCard;
    @Column(name = "CAR_IMG")
    private String carImg;
    @Column(name = "DESCRIPTION")
    private String description;

    public Vehicle(){}
    public Vehicle(String userId, String plateNumber){
        this.userId = userId;
        this.plateNumber = plateNumber;
    }

    public String getId(){return id;}
    public void setId(String id){this.id = id;}

    public String getUserId(){return userId;}
    public void setUserId(String userId){this.userId = userId;}

    public String getPlateNumber(){return plateNumber;}
    public void setPlateNumber(String plateNumber){this.plateNumber = plateNumber;}

    public String getCarCard(){return carCard;}
    public void setCarCard(String carCard){this.carCard = carCard;}

    public String getCarImg(){return carImg;}
    public void setCarImg(String carImg){this.carImg = carImg;}

    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}
}
