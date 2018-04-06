package com.taxikar.controllers;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.taxikar.bean.BaseResponse;
import com.taxikar.bean.UsersInfo;
import com.taxikar.bean.request.FavoritesRequest;
import com.taxikar.entity.Favorites;
import com.taxikar.entity.Users;
import com.taxikar.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Jamasb on 2/10/2018.
 */

@Controller
public class AccountController
{
    private  Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/SendSMS/{mobileNumber}",method = RequestMethod.POST )
    @ResponseBody
    public BaseResponse SendSMS(@PathVariable String mobileNumber)
    {
        logger.info("Sending SMS is Started");
        return accountService.SendSMS(mobileNumber);
    }

    @RequestMapping(value = "/Login/{mobileNumber}/{rand}",method = RequestMethod.POST )
    @ResponseBody
    public BaseResponse Login(@PathVariable String mobileNumber,@PathVariable String rand)
    {
        logger.debug("Starting Login Process");
        return accountService.Login(mobileNumber,rand);
    }

    @RequestMapping(value="/Account/EditUser/{mobileNumber}",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse EditUser(@RequestBody UsersInfo request, @PathVariable String mobileNumber)
    {
        logger.info("Editing Users Info");
        return accountService.EditUser(request,mobileNumber);
    }

    @RequestMapping(value = "/Account/GetUserInfo/{mobileNumber}",method = RequestMethod.POST)
    @ResponseBody
    public UsersInfo GetUserInfo(@PathVariable String mobileNumber)
    {
        logger.info("Passing Users Info to Client side UI");
        return accountService.GetUserInfo(mobileNumber);
    }
    @RequestMapping(value = "/Account/GetUserStatus/{mobileNumber}",method = RequestMethod.POST)
    @ResponseBody
    public int GetUserStatus(@PathVariable String mobileNumber)
    {
        return accountService.GetUserStatus(mobileNumber);
    }

    @RequestMapping(value = "/Account/AddFavorite/{PhoneNumber}",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse AddFavorite(@RequestBody FavoritesRequest AddFavoriteRequest,@PathVariable String PhoneNumber)
    {
        return  accountService.AddFavorite(AddFavoriteRequest,PhoneNumber);
    }

    @RequestMapping(value = "/Account/EditFavorite/{FavoriteID}",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse EditFavorite(@RequestBody FavoritesRequest AddFavoriteRequest,@PathVariable String FavoriteID)
    {
        return  accountService.EditFavorite(AddFavoriteRequest,FavoriteID);
    }

    @RequestMapping(value = "/Account/RemoveFavorite/{FavoriteID}",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse RemoveFavorite(@PathVariable String FavoriteID)
    {
        return  accountService.RemoveFavorite(FavoriteID);
    }

    @RequestMapping(value = "/Account/GetFavoriteList/{PhoneNumber}",method = RequestMethod.POST)
    @ResponseBody
    public List<Favorites> GetFavoriteList(@PathVariable String PhoneNumber)
    {
        return accountService.GetFavoriteList(PhoneNumber);
    }

    @RequestMapping(value = "/Account/AddFavoriteUsage/{FavoriteID}",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse AddFavoriteUsage(@PathVariable String FavoriteID)
    {
        return  accountService.AddFavoriteUsage(FavoriteID);
    }


}

