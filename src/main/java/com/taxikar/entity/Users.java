package com.taxikar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by parinaz on 2/8/2018.
 */
@Entity
@Table(name = "users")
public class Users {
    @Id
    private String id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "MOBILE_NUMBER")
    private Long mobileNumber;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "STATUS")
    private int status;
    @Column(name = "SEX")
    private int sex;
    @Column(name = "BIRTHDAY")
    private Timestamp birthday;
    @Column(name = "DRIVER_DETAIL")
    private DriverDetail driverDetail;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "USER_IMG")
    private String userImg;

    public Users(){}
    public Users(String firstName, String lastName, Long mobileNumber, String email, int status){
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.status = status;
    }

    public String getId(){return id;}
    public void setId(String id){this.id = id;}

    public String getFirstName(){return firstName;}
    public void setFirstName(String firstName){this.firstName = firstName;}

    public String getLastName(){return lastName;}
    public void setLastName(String lastName){this.lastName = lastName;}

    public Long getMobileNumber(){return mobileNumber;}
    public void setMobileNumber(Long mobileNumber){this.mobileNumber = mobileNumber;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public int getStatus(){return status;}
    public void setStatus(int status){this.status = status;}

    public int getSex(){return sex;}
    public void setSex(int sex){this.sex = sex;}

    public Timestamp getBirthday(){return birthday;}
    public void setBirthday(Timestamp birthday){this.birthday = birthday;}

    public DriverDetail getDriverDetail(){return driverDetail;}
    public void setDriverDetail(DriverDetail driverDetail){this.driverDetail = driverDetail;}

    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}

    public String getUserImg(){return userImg;}
    public void setUserImg(String userImg){this.userImg = userImg;}
}
