package com.taxikar.bean;
import com.taxikar.entity.DriverDetail;

import java.sql.Timestamp;

public class UsersInfo
{
    private String Id;



    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private int status;
    private int sex;
    private Timestamp birthday;
    private DriverDetail driverDetail;
    private String description;
    private String userImg;

    public UsersInfo() { }
    public UsersInfo(String firstName, String lastName, String mobileNumber, String email, int status, int sex, Timestamp birthday, DriverDetail driverDetail, String description, String userImg,String id)
    {
        this.Id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.status = status;
        this.sex = sex;
        this.birthday = birthday;
        this.driverDetail = driverDetail;
        this.description = description;
        this.userImg = userImg;
    }
    public String getId()
    {
        return Id;
    }

    public void setId(String id)
    {
        Id = id;
    }
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getSex()
    {
        return sex;
    }

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    public Timestamp getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Timestamp birthday)
    {
        this.birthday = birthday;
    }

    public DriverDetail getDriverDetail()
    {
        return driverDetail;
    }

    public void setDriverDetail(DriverDetail driverDetail)
    {
        this.driverDetail = driverDetail;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getUserImg()
    {
        return userImg;
    }

    public void setUserImg(String userImg)
    {
        this.userImg = userImg;
    }
}
