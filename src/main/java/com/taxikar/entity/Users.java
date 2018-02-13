package com.taxikar.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by parinaz on 2/8/2018.
 */
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(columnDefinition = "char(36)")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "STATUS")
    private int status;
    @Column(name = "SEX")
    private int sex;
    @Column(name = "BIRTHDAY")
    private Timestamp birthday;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DRIVER_DETAIL")
    private DriverDetail driverDetail;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "USER_IMG")
    private String userImg;
    @Column(name = "TOKEN")
    private String token;
    @Column(name = "TOKEN_TIMESTAMP")
    private Timestamp tokenTimeStamp;


    public Users(String mobileNumber,String token)
    {
        this.mobileNumber=mobileNumber;status=1;this.token=token;
        this.status=0;
    }
    public Users(String firstName, String lastName, String mobileNumber, String email, int status){
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

    public String getMobileNumber(){return mobileNumber;}
    public void setMobileNumber(String mobileNumber){this.mobileNumber = mobileNumber;}

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

    public String getToken()
    {
        return token;
        /*this.tokenTimeStamp = new Timestamp(System.currentTimeMillis());
        if((tokenTimeStamp+(5*60*1000)) < (System.currentTimeMillis()))
            return  "PassedTimeToken";
        return token;*/
    }

    public void setToken(String token)
    {
        this.token = token;
        /*this.token = token;
        this.tokenTimeStamp=System.currentTimeMillis();*/
    }

    public Timestamp getTokenTimeStamp(){return tokenTimeStamp;}
    public void setTokenTimeStamp(Timestamp tokenTimeStamp){this.tokenTimeStamp = tokenTimeStamp;}

}
