package com.taxikar.controllers;

import com.taxikar.bean.BaseResponse;
import com.taxikar.bean.request.EditJourneyPassengerRequest;
import com.taxikar.bean.request.JourneyPassenger;
import com.taxikar.service.JourneyPassengerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by parinaz on 2/10/2018.
 */
public class JourneyPassengerController {
    Logger logger = LoggerFactory.getLogger(JourneyPassengerController.class);
    private JourneyPassengerService journeyPassengerService;

    @Autowired
    public JourneyPassengerController(JourneyPassengerService journeyPassengerService){
        this.journeyPassengerService = journeyPassengerService;
    }

    @RequestMapping("/journey/passenger/add")
    @ResponseBody
    public BaseResponse addJourney(JourneyPassenger request){
        logger.info("saving a journey created by passenger");
        return journeyPassengerService.addJourney(request);
    }

    @RequestMapping("/journey/passenger/delete")
    @ResponseBody
    public BaseResponse deleteJourney(String id){
        logger.info("removing journey with id "+id);
        return journeyPassengerService.deleteJourney(id);
    }


    @RequestMapping("/journey/passenger/edit")
    @ResponseBody
    public BaseResponse editJourney(@RequestBody EditJourneyPassengerRequest request){
        logger.info("Ediding user with id "+request.getId());
        return journeyPassengerService.editJourney(request);
    }
}
