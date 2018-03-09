package com.taxikar.service;

import com.fasterxml.jackson.databind.ser.Serializers;

import com.kavenegar.sdk.KavenegarApi;
import com.kavenegar.sdk.models.SendResult;
import com.taxikar.bean.BaseResponse;
import com.taxikar.bean.UsersInfo;
import com.taxikar.controllers.AccountController;
import com.taxikar.entity.Users;
import com.taxikar.enums.ResponseStatus;
import com.taxikar.repository.UserRepository;

import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Random;


@Service
public class AccountService
{
    private Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Value("${sms.api}")
    private String smsAPI;
    @Autowired
    private UserRepository userRepository;
    // send sms and login should be with registration
    public BaseResponse SendSMS(String mobileNumber)
    {

        BaseResponse baseResponse=new BaseResponse();

        if(!isValidPhoneNumber(mobileNumber))
        {
            baseResponse.setStatus(0);
            baseResponse.setErrorMessage("Not valid Number");
            return  baseResponse;
        }
        Random random = new Random();
        String rand=String.format("%04d", random.nextInt(10000));
        Users user=userRepository.FindUserByMobileNumber(mobileNumber);
        if(user!=null)
        {
            user.setSmsCount(user.getSmsCOUNT()+1);
            if(user.getSmsCOUNT()>5)
            {
                baseResponse.setErrorMessage("Too many Attempts");
                baseResponse.setStatus(0);
                return baseResponse;
            }
            user.setToken(rand);
            user.setTokenTimeStamp(new Timestamp(System.currentTimeMillis()));
        }
        else
        {
            user = new Users(mobileNumber, rand,0);
//            userRepository.save(user);
        }
        try
        {
            KavenegarApi api=new KavenegarApi(smsAPI);
            SendResult sendResult= api.verifyLookup(mobileNumber,rand,"","","NettroOtp");

            if(sendResult.getStatus()==200)
                baseResponse.setStatus(1);
            else
            {
                baseResponse.setStatus(sendResult.getStatus());
                baseResponse.setErrorMessage(sendResult.getMessage());
            }
        }
        catch (Exception P)
        {
            baseResponse.setErrorMessage(P.toString());
            baseResponse.setStatus(0);
        }
        return  baseResponse;
    }
    private boolean isValidPhoneNumber(String mobileNumber){
        String regEx = "^09[0-9]{9}$";
        return mobileNumber.matches(regEx);
    }


    public BaseResponse Login(String mobileNumber, String rand)
    {
        BaseResponse baseResponse=new BaseResponse();
        if(mobileNumber.length()!=11)
        {
            baseResponse.setStatus(0);
            baseResponse.setErrorMessage("Not valid Number");
            return baseResponse;
        }
        Users user=userRepository.FindUserByMobileNumber(mobileNumber);
        if(user==null)
        {
            baseResponse.setStatus(0);
            baseResponse.setErrorMessage("This Number dosn't exist in DB,First ask for sending SMS to it");
            return baseResponse;
        }
        user.setSmsCount(0);
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
