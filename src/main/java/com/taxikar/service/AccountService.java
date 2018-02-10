package com.taxikar.service;

import com.taxikar.bean.UsersInfo;
import com.taxikar.entity.Users;
import com.taxikar.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Random;

@Service
public class AccountService
{
    @Autowired
    private UserRepository userRepository;

    public String SendSMS(String mobileNumber)
    {
        Random random = new Random();
        String rand=String.format("%04d", random.nextInt(10000));
        try
        {
            //send SMS TO mobileNumber : rand
            //********* Need Edit
        }
        catch (Exception P)
        {
            rand="error";
        }
        return  rand;
    }
    public String Login(String mobileNumber)
    {
        // go find user with this mobile number in table Users if the was'nt any --->
            new Users(mobileNumber);
        //      2.return "NewUser";
        //else find user whit this mobile number in table Users and ----> return user.Status
        //********* Need Edit of crud repository
        return "teMP";
    }


    public Boolean EditUser(UsersInfo NewUserValues, String mobileNumber)
    {
        // ********* Need Edit
        // 1. find the user with this phone number
        Users selectedUser=new Users(mobileNumber);//after doing above line we will have an user and this line should be deleted

        selectedUser.setBirthday(NewUserValues.getBirthday());
        selectedUser.setDescription(NewUserValues.getDescription());
        selectedUser.setDriverDetail(NewUserValues.getDriverDetail());
        selectedUser.setEmail(NewUserValues.getEmail());
        selectedUser.setFirstName(NewUserValues.getFirstName());
        selectedUser.setLastName(NewUserValues.getLastName());
        selectedUser.setSex(NewUserValues.getSex());
        selectedUser.setUserImg(NewUserValues.getUserImg());
        return true;
        // True means done successfully false means otherwise
    }
    public UsersInfo GetUserInfo(String mobileNumber)
    {
        //********* Need Edit
        //find user with this mobile number
        Users selectedUser=new Users(mobileNumber);//after doing above line we will have an user and this line should be deleted

        return new UsersInfo(selectedUser.getFirstName(),selectedUser.getLastName(),selectedUser.getMobileNumber(),selectedUser.getEmail(),selectedUser.getStatus(),selectedUser.getSex(),selectedUser.getBirthday(),selectedUser.getDriverDetail(),selectedUser.getDescription(),selectedUser.getUserImg());
    }
}
