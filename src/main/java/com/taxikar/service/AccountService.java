package com.taxikar.service;

import com.kavenegar.sdk.KavenegarApi;
import com.kavenegar.sdk.models.SendResult;
import com.taxikar.bean.BaseResponse;
import com.taxikar.bean.UsersInfo;
import com.taxikar.bean.request.FavoritesRequest;
import com.taxikar.controllers.AccountController;
import com.taxikar.entity.DriverDetail;
import com.taxikar.entity.Favorites;
import com.taxikar.entity.Users;
import com.taxikar.repository.DriverDetailRepository;
import com.taxikar.repository.FavoritesRepository;
import com.taxikar.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
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
    @Autowired
    private FavoritesRepository favoritesRepository;
    @Autowired
    private DriverDetailRepository driverDetailRepository;



    // send sms and login should be with registration
    public BaseResponse SendSMS(String mobileNumber)
    {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(1);
        baseResponse.setErrorMessage("کد شناسایی با موفقیت ارسال شد.");

        if (!isValidPhoneNumber(mobileNumber))
        {
            baseResponse.setStatus(0);
            baseResponse.setErrorMessage("شماره تلفن وارد شده درست نیست.");
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

                    baseResponse.setErrorMessage("تعداد تلاشها برای ورود بیش از حد مجاز");
                    baseResponse.setStatus(0);
                    baseResponse.setUserID(user.getId());
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
            if(sendResult.getStatus() == 6 ||sendResult.getStatus() == 11 ||sendResult.getStatus() == 13)
            {
                baseResponse.setStatus(0);
                baseResponse.setErrorMessage(sendResult.getMessage());
            }
            else
                baseResponse.setStatus(1);
            baseResponse.setErrorCode(sendResult.getStatus());
        }
        catch (Exception P)
        {
            baseResponse.setErrorMessage("ارسال کد شماسایی از طریق سامانه ارسال پیامک با مشکل همراه بوده است.");
            baseResponse.setStatus(0);
        }
        baseResponse.setUserID(user.getId());
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
            baseResponse.setErrorMessage("Given Phone number is not right");
            return baseResponse;
        }
        Users user = userRepository.FindUserByMobileNumber(mobileNumber);
        if (user == null)
        {
            baseResponse.setStatus(0);
            baseResponse.setErrorMessage("This Number doesn’t exist in Database, this means we haven't send any sms to it,so we are not waiting for any token which u are trying to send us, First ask for Sending SMS to this number");
            return baseResponse;
        }
        baseResponse.setUserID(user.getId());
        if (user.getTokenTimeStamp().before(new Timestamp(System.currentTimeMillis()-smsTokenValidDurationInSec*1000)))
        {
            baseResponse.setStatus(0);
            baseResponse.setErrorMessage("کد وارد شده درست نیست.");
            return baseResponse;
        }
        if(user.getToken()==null||!user.getToken().equals(rand))
        {
            baseResponse.setStatus(0);
            baseResponse.setErrorMessage("کد وارد شده درست نیست");
            return baseResponse;
        }
        user.setSmsCount(0);
        user.setToken(null);
        userRepository.save(user);
        baseResponse.setStatus(1);
        baseResponse.setErrorMessage("Ok, you are in...Login successful, The assigned token is now Destroyed,You can't Use it any More");
        return baseResponse;
    }

    public BaseResponse EditUser(UsersInfo NewUserValues, String mobileNumber)
    {
        BaseResponse baseResponse = new BaseResponse();

        // 1. find the user with this phone number
        Users selectedUser = userRepository.FindUserByMobileNumber(mobileNumber);
        if(selectedUser==null)
        {
            baseResponse.setErrorMessage("this Phone Number doesn’t exist in DB,either you are stuPid in tyPing :D or the backend is hacked !! ");
            baseResponse.setStatus(0);
        }
        baseResponse.setUserID(selectedUser.getId());
        if(NewUserValues.getBirthday()!=null)
            selectedUser.setBirthday(NewUserValues.getBirthday());
        if(NewUserValues.getDescription()!=null)
            selectedUser.setDescription(NewUserValues.getDescription());

 //       if(NewUserValues.getDriverDetail().getLisenceImg()!=null)
   //    {
            DriverDetail temp=selectedUser.getDriverDetail();
            if(temp==null)
                temp=new DriverDetail();
            temp.setInsuranceImg(NewUserValues.getDriverDetail().getInsuranceImg());
            temp.setLisenceImg(NewUserValues.getDriverDetail().getLisenceImg());
            temp.setStatus(NewUserValues.getDriverDetail().getStatus());
            selectedUser.setDriverDetail(temp);
            driverDetailRepository.save(temp);
    //    }


        if(NewUserValues.getEmail()!=null)
        selectedUser.setEmail(NewUserValues.getEmail());
        if(NewUserValues.getFirstName()!=null)
            selectedUser.setFirstName(NewUserValues.getFirstName());
        if(NewUserValues.getLastName()!=null)
            selectedUser.setLastName(NewUserValues.getLastName());
        if(NewUserValues.getSex()!=0 )
            selectedUser.setSex(NewUserValues.getSex());
        if(NewUserValues.getUserImg()!=null)
            selectedUser.setUserImg(NewUserValues.getUserImg());

        userRepository.save(selectedUser);

        baseResponse.setStatus(1);
        baseResponse.setErrorMessage("No Error, Good... by the way let's talk... I'm a lonely Compiled Code What about you? tell Me More about yourself :)");
        return baseResponse;
    }

    public UsersInfo GetUserInfo(String mobileNumber)
    {
        Users selectedUser = userRepository.FindUserByMobileNumber(mobileNumber);
        return new UsersInfo(selectedUser.getFirstName(), selectedUser.getLastName(), selectedUser.getMobileNumber(), selectedUser.getEmail(), selectedUser.getStatus(), selectedUser.getSex(), selectedUser.getBirthday(), selectedUser.getDriverDetail(), selectedUser.getDescription(), selectedUser.getUserImg(),selectedUser.getId());
    }

    public int GetUserStatus(String mobileNumber)
    {
        return userRepository.FindUserByMobileNumber(mobileNumber).getStatus();
    }
//    public BaseResponse DeleteUser(String PhoneNumber)
//    {
//        BaseResponse baseResponse=new BaseResponse();
//        baseResponse.setStatus(1);
//        baseResponse.setErrorMessage("Successful");
//        Users selectedUser=userRepository.FindUserByMobileNumber(PhoneNumber);
//        if(selectedUser==null)
//        {
//            baseResponse.setStatus(0);
//            baseResponse.setErrorMessage("No user with this Phone Number");
//            return baseResponse;
//        }
//        baseResponse.setUserID(selectedUser.getId());
//
//
//        ////////////////////////////////////////////////////////////////////////////////////////////
//        /////////////////////////////////////
//        //////////////////////////////////////////////////////////
//        ////////////////////////////////////////////////////////////////////////////////////////
//        ////////////////
//        /////////////////////////////////////
//        //////////////////////////////////////////////////////////
//        ////////////////////////////////////////////////////////////////////////////////////////
//        //PPPPPut Every other table here
//        for(int P=0;P<favoritesRepository.getAllFavorites(selectedUser.getId()).size();P++)
//        {
//            Favorites temp=favoritesRepository.getAllFavorites(selectedUser.getId()).get(P);
//            favoritesRepository.delete(temp);
//        }
//        userRepository.delete(selectedUser);
//
//
//        return baseResponse;
//    }

    public BaseResponse AddFavorite(FavoritesRequest addFavoriteRequest, String PhoneNumber)
    {
        BaseResponse baseResponse=new BaseResponse();
        baseResponse.setStatus(1);
        baseResponse.setErrorMessage("Successful");

        Users selectedUser=userRepository.FindUserByMobileNumber(PhoneNumber);
        if(selectedUser==null)
        {
            baseResponse.setStatus(0);
            baseResponse.setErrorMessage("No user with this Phone Number");
            return baseResponse;
        }

        baseResponse.setUserID(selectedUser.getId());

        Favorites favorite=new Favorites(selectedUser.getId(),addFavoriteRequest.getFavoriteName(),addFavoriteRequest.getFavoritePos(),new Timestamp(System.currentTimeMillis()),"0");
        favoritesRepository.save(favorite);
        return baseResponse;
    }

    public BaseResponse RemoveFavorite(String favoriteID)
    {
        BaseResponse baseResponse=new BaseResponse();
        baseResponse.setStatus(1);
        baseResponse.setErrorMessage("Successful");

        Favorites favorite=favoritesRepository.getOneFavorite(favoriteID);
        if(favorite==null)
        {
            baseResponse.setStatus(0);
            baseResponse.setErrorMessage("No favorite with this ID:"+favoriteID);
            return baseResponse;
        }
        favoritesRepository.delete(favorite);

        return baseResponse;
    }

    public BaseResponse EditFavorite(FavoritesRequest editFavoriteRequest, String favoriteID)
    {
        BaseResponse baseResponse=new BaseResponse();
        baseResponse.setStatus(1);
        baseResponse.setErrorMessage("Successful");

        Favorites favorite=favoritesRepository.getOneFavorite(favoriteID);
        if(favorite==null)
        {
            baseResponse.setStatus(0);
            baseResponse.setErrorMessage("a:"+favoriteID);
            return baseResponse;
        }
        favorite.setFavoriteName(editFavoriteRequest.getFavoriteName());
        favorite.setFavoritePos(editFavoriteRequest.getFavoritePos());
        favorite.setFavoriteTimeStamp(new Timestamp(System.currentTimeMillis()));

        favoritesRepository.save(favorite);
        return baseResponse;
    }

    public List<Favorites> GetFavoriteList(String phoneNumber)
    {
        Users selectedUser=userRepository.FindUserByMobileNumber(phoneNumber);
        if(selectedUser==null)
            return null;

        List<Favorites> favoriteList = favoritesRepository.getAllFavorites(selectedUser.getId());
        return favoriteList;
    }

    public BaseResponse AddFavoriteUsage(String favoriteID)
    {
        BaseResponse baseResponse=new BaseResponse();
        baseResponse.setStatus(1);
        baseResponse.setErrorMessage("Successful");

        Favorites favorite=favoritesRepository.getOneFavorite(favoriteID);
        if(favorite==null)
        {
            baseResponse.setStatus(0);
            baseResponse.setErrorMessage("No favorite with this ID:"+favoriteID);
            return baseResponse;
        }
        favorite.setFavoriteNumberOFUses((Integer.parseInt(favorite.getFavoriteNumberOFUses())+1)+"");
        favoritesRepository.save(favorite);

        return baseResponse;
    }


}
