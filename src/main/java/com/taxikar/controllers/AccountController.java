package com.taxikar.controllers;

import com.taxikar.bean.BaseResponse;
import com.taxikar.bean.UsersInfo;
import com.taxikar.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jamasb on 2/10/2018.
 */

@Controller
public class AccountController
{
    private  Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;


    @RequestMapping(value = "/SendSMS/{mobileNumber}",method = RequestMethod.GET )
    public BaseResponse SendSMS(@PathVariable String mobileNumber)
    {
        logger.debug("Sending SMS");
        return accountService.SendSMS(mobileNumber);
    }

    @RequestMapping(value = "/Login/{mobileNumber}/{rand}",method = RequestMethod.POST )
    public BaseResponse Login(@PathVariable String mobileNumber,@PathVariable String rand)
    {
        logger.debug("Starting Login Process");
        return accountService.Login(mobileNumber,rand);
    }

    @RequestMapping(value="/Account/EditUser/{mobileNumber}")
    public BaseResponse EditUser(@RequestBody UsersInfo request, @PathVariable String mobileNumber)
    {
        logger.debug("Editing Users Info");
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
}

