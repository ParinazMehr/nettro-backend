package com.taxikar.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by parinaz on 2/8/2018.
 */
@Entity
@Table(name = "driver_detail")
public class DriverDetail {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(columnDefinition = "char(36)")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    @Column(name = "STATUS")
    private int status;
    @Column(name = "LICENCE_IMG")
    private String licenceImg;
    @Column(name = "INSURANCE_IMG")
    private String insuranceImg;

    public DriverDetail(){}
    public DriverDetail(int status){this.status = status;}

    public String getId(){return id;}
    public void setId(String id){this.id = id;}

    public int getStatus(){return status;}
    public void setStatus(int status){this.status = status;}

    public String getLisenceImg(){return licenceImg;}
    public void setLisenceImg(String lisenceImg){this.licenceImg = lisenceImg;}

    public String getInsuranceImg(){return insuranceImg;}
    public void setInsuranceImg(String insuranceImg){this.insuranceImg = insuranceImg;}
}
