package com.taxikar.controllers;

import com.taxikar.bean.UsersInfo;
import com.taxikar.entity.Users;
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

    @RequestMapping(value = "/SendSMS/{mobileNumber}",method = RequestMethod.POST )
    public String SendSMS(@PathVariable String mobileNumber)
    {
        logger.debug("Sending SMS");
        return accountService.SendSMS(mobileNumber);
    }

    @RequestMapping(value = "/Login/{mobileNumber}",method = RequestMethod.POST )
    public String Login(@PathVariable String mobileNumber)
    {
        logger.debug("Starting Login Process");
        return accountService.Login(mobileNumber);
    }

    @RequestMapping(value="/Account/EditUser/{mobileNumber}")
    public boolean EditUser(@RequestBody UsersInfo request,@PathVariable String mobileNumber)
    {
        logger.debug("Editing Users Info");
        return accountService.EditUser(request,mobileNumber);
    }

    @RequestMapping(value = "/Account/GetUserInfo/{mobileNumber}",method = RequestMethod.POST)
    public UsersInfo GetUserInfo(@PathVariable String mobileNumber)
    {
        logger.debug("Passing Users Info to Client side UI");
        return accountService.GetUserInfo(mobileNumber);
    }
}

