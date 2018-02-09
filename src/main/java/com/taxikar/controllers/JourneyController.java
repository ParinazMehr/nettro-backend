package com.taxikar.controllers;

import com.taxikar.bean.request.JurneyDriverRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by parinaz on 2/2/2018.
 */
@Controller
public class JourneyController {
    Logger logger = LoggerFactory.getLogger(JourneyController.class);

    @RequestMapping("/jurney/driver/add")
    public void addJourneyDriver(@RequestBody JurneyDriverRequest request){
        logger.debug("adding a journey from driver to database");

    }
}