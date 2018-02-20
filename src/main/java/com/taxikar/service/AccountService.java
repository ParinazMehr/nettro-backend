package com.taxikar.service;

import com.fasterxml.jackson.databind.ser.Serializers;

import com.kavenegar.sdk.KavenegarApi;
import com.taxikar.bean.BaseResponse;
import com.taxikar.bean.UsersInfo;
import com.taxikar.entity.Users;
import com.taxikar.enums.ResponseStatus;
import com.taxikar.repository.UserRepository;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Random;

@Service
public class AccountService
{
    @Autowired
    private UserRepository userRepository;
    // send sms and login should be with registration
    public BaseResponse SendSMS(String mobileNumber)
    {
        BaseResponse baseResponse=new BaseResponse();
        if(mobileNumber.length()!=11)
        {
            baseResponse.setStatus(0);
            baseResponse.setErrorMessage("Not valid Number");
        }
        Random random = new Random();
        String rand=String.format("%04d", random.nextInt(10000));
        Users user=userRepository.FindUserByMobileNumber(mobileNumber);
        if(user!=null)
        {
            user.setToken(rand);
            user.setTokenTimeStamp(new Timestamp(System.currentTimeMillis()));
        }
        else
            user=new Users(mobileNumber,rand);
        try
        {
            KavenegarApi api=new KavenegarApi("3974693536534143426E733743665170473134384C2F4D2B43417469696A702B");
            api.verifyLookup(mobileNumber,rand,"","","NettroOtp");
            baseResponse.setStatus(1);
        }
        catch (Exception P)
        {
            baseResponse.setErrorMessage(P.toString());
            baseResponse.setStatus(0);
        }
        return  baseResponse;
    }
    public BaseResponse Login(String mobileNumber, String rand)
    {
        BaseResponse baseResponse=new BaseResponse();
        Users user=userRepository.FindUserByMobileNumber(mobileNumber);
        if(!user.getToken().equals(rand)  || (user.getTokenTimeStamp().before(new Timestamp(System.currentTimeMillis()))))
        {
            baseResponse.setStatus(0);
            baseResponse.setErrorMessage("Not Valid Code Entered");
            return baseResponse;
        }
        baseResponse.setStatus(1);
        return baseResponse;
    }
    public BaseResponse EditUser(UsersInfo NewUserValues, String mobileNumber)
    {
        BaseResponse baseResponse=new BaseResponse();
        try
        {
            // 1. find the user with this phone number
            Users selectedUser = userRepository.FindUserByMobileNumber(mobileNumber);
            selectedUser.setBirthday(NewUserValues.getBirthday());
            selectedUser.setDescription(NewUserValues.getDescription());
            selectedUser.setDriverDetail(NewUserValues.getDriverDetail());
            selectedUser.setEmail(NewUserValues.getEmail());
            selectedUser.setFirstName(NewUserValues.getFirstName());
            selectedUser.setLastName(NewUserValues.getLastName());
            selectedUser.setSex(NewUserValues.getSex());
            selectedUser.setUserImg(NewUserValues.getUserImg());
            userRepository.save(selectedUser);
            baseResponse.setStatus(1);
        }
        catch (Exception error)
        {
            baseResponse.setErrorMessage(error.toString());
            baseResponse.setStatus(0);
        }
        return  baseResponse;
    }
    public UsersInfo GetUserInfo(String mobileNumber)
    {
        Users selectedUser=userRepository.FindUserByMobileNumber(mobileNumber);
        return new UsersInfo(selectedUser.getFirstName(),selectedUser.getLastName(),selectedUser.getMobileNumber(),selectedUser.getEmail(),selectedUser.getStatus(),selectedUser.getSex(),selectedUser.getBirthday(),selectedUser.getDriverDetail(),selectedUser.getDescription(),selectedUser.getUserImg());
    }
    public int GetUserStatus(String mobileNumber)
    {
        return userRepository.FindUserByMobileNumber(mobileNumber).getStatus();
    }
}
