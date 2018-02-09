package com.taxikar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by parinaz on 2/8/2018.
 */
@Entity
@Table(name = "driver_detail")
public class DriverDetail {
    @Id
    private Long id;
    @Column(name = "STATUS")
    private int status;
    @Column(name = "LICENCE_IMG")
    private String licenceImg;
    @Column(name = "INSURANCE_IMG")
    private String insuranceImg;

    public DriverDetail(){}
    public DriverDetail(int status){this.status = status;}

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public int getStatus(){return status;}
    public void setStatus(int status){this.status = status;}

    public String getLisenceImg(){return licenceImg;}
    public void setLisenceImg(String lisenceImg){this.licenceImg = lisenceImg;}

    public String getInsuranceImg(){return insuranceImg;}
    public void setInsuranceImg(String insuranceImg){this.insuranceImg = insuranceImg;}
}
