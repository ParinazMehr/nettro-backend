package com.taxikar.service;

import com.kavenegar.sdk.KavenegarApi;
import com.kavenegar.sdk.models.SendResult;
import com.taxikar.bean.BaseResponse;
import com.taxikar.bean.UsersInfo;
import com.taxikar.controllers.AccountController;
import com.taxikar.entity.Users;
import com.taxikar.repository.UserRepository;
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
    @Value("${sms.token.validDuration.inSec}")
    private int smsTokenValidDurationInSec;
    @Value("${sms.maxNum.rapidSending}")
    private int smsMaxNumRapidSending;
    @Value("${sms.timeToWaitForNewAttempt.inSec}")
    private int smsTimeToWaitForNewAttemptInSec;

    @Autowired
    private UserRepository userRepository;

    // send sms and login should be with registration
    public BaseResponse SendSMS(String mobileNumber)
    {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(1);
        baseResponse.setErrorMessage("SMS Sent Successfully");

        if (!isValidPhoneNumber(mobileNumber))
        {
            baseResponse.setStatus(0);
            baseResponse.setErrorMessage("Entered Phone number is not valid");
            return baseResponse;
        }
        Random random = new Random();
        String rand = String.format("%04d", random.nextInt(10000));
        Users user = userRepository.FindUserByMobileNumber(mobileNumber);
        if (user != null)
        {
            if (user.getSmsCount() > smsMaxNumRapidSending)
            {
                if (new Timestamp(System.currentTimeMillis()).getTime() < (smsTimeToWaitForNewAttemptInSec*1000 + user.getSmsCount_TimeStamp().getTime()))
                {

                    baseResponse.setErrorMessage("Too many Attempts");
                    baseResponse.setStatus(0);
                    return baseResponse;
                }
                else
                    user.setSmsCount(1);
            }
            else
            {
                if(user.getSmsCount()==5)
                   user.setSmsCount_TimeStamp(new Timestamp(System.currentTimeMillis()));
                user.setSmsCount(user.getSmsCount() + 1);
            }

            user.setToken(rand);
            user.setTokenTimeStamp(new Timestamp(System.currentTimeMillis()));
        }
        else
            user = new Users(mobileNumber, rand, 1, new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
        try
        {
            KavenegarApi api = new KavenegarApi(smsAPI);
            SendResult sendResult = api.verifyLookup(mobileNumber, rand, "", "", "NettroOtp");

            if (sendResult.getStatus() == 200)
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
        return baseResponse;
    }

    private boolean isValidPhoneNumber(String mobileNumber)
    {
        String regEx = "^09[0-9]{9}$";
        return mobileNumber.matches(regEx);
    }


    public BaseResponse Login(String mobileNumber, String rand)
    {
        BaseResponse baseResponse = new BaseResponse();
        if (!isValidPhoneNumber(mobileNumber))
        {
            baseResponse.setStatus(0);
            baseResponse.setErrorMessage("Entered Phone number is not valid");
            return baseResponse;
        }
        Users user = userRepository.FindUserByMobileNumber(mobileNumber);
        if (user == null)
        {
            baseResponse.setStatus(0);
            baseResponse.setErrorMessage("This Number dosn't exist in Database,this means we haven't send any sms to it,so we are not waiting for any token which u are trying to send us, First ask for Sending SMS to this number");
            return baseResponse;
        }
        if (user.getTokenTimeStamp().before(new Timestamp(System.currentTimeMillis()-smsTokenValidDurationInSec*1000)))
        {
            baseResponse.setStatus(0);
            baseResponse.setErrorMessage("Not Valid Code Entered PM");
            return baseResponse;
        }
        if(user.getToken()==null||!user.getToken().equals(rand))
        {
            baseResponse.setStatus(0);
            baseResponse.setErrorMessage("the Entered Code is not correct");
            return baseResponse;
        }
        user.setSmsCount(0);
        user.setToken(null);
        userRepository.save(user);
        baseResponse.setStatus(1);
        baseResponse.setErrorMessage("Ok, you are in...Login successfull,The assigned token is now Destroyed,You can't Use it any More");
        return baseResponse;
    }

    public BaseResponse EditUser(UsersInfo NewUserValues, String mobileNumber)
    {
        BaseResponse baseResponse = new BaseResponse();

        // 1. find the user with this phone number
        Users selectedUser = userRepository.FindUserByMobileNumber(mobileNumber);
        if(selectedUser==null)
        {
            baseResponse.setErrorMessage("this Phone Number does'nt exist in DB,either your are stuPid in tyPing :D or the backend is hacked !! ");
            baseResponse.setStatus(0);
        }
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
        baseResponse.setErrorMessage("No Error,Good... by the way let's talk... I'm a lonely Compiled Code What about you? tell me about your self ");
        return baseResponse;
    }

    public UsersInfo GetUserInfo(String mobileNumber)
    {
        Users selectedUser = userRepository.FindUserByMobileNumber(mobileNumber);
        return new UsersInfo(selectedUser.getFirstName(), selectedUser.getLastName(), selectedUser.getMobileNumber(), selectedUser.getEmail(), selectedUser.getStatus(), selectedUser.getSex(), selectedUser.getBirthday(), selectedUser.getDriverDetail(), selectedUser.getDescription(), selectedUser.getUserImg());
    }

    public int GetUserStatus(String mobileNumber)
    {
        return userRepository.FindUserByMobileNumber(mobileNumber).getStatus();
    }
}
