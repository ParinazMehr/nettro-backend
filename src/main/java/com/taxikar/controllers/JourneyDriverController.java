package com.taxikar.controllers;

import com.taxikar.bean.request.EditJourneyDriverRequest;
import com.taxikar.bean.request.JurneyDriverRequest;
import com.taxikar.bean.response.JourneyDriverBaseResponse;
import com.taxikar.services.JourneyDriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by parinaz on 2/2/2018.
 */
@Controller
public class JourneyDriverController {
    Logger logger = LoggerFactory.getLogger(JourneyDriverController.class);
    private JourneyDriverService journeyDriverService;

    @Autowired
    public JourneyDriverController(JourneyDriverService journeyDriverService){
        this.journeyDriverService = journeyDriverService;
    }

    @RequestMapping("/journey/driver/add")
    @ResponseBody
    public JourneyDriverBaseResponse addJourneyDriver(@RequestBody JurneyDriverRequest request){
        logger.info("adding a journey from driver to database");
        return journeyDriverService.addNewJourney(request);
    }

    @RequestMapping("journey/driver/delete")
    @ResponseBody
    public JourneyDriverBaseResponse deleteJourney(@RequestBody String id){
        logger.info("deleting journey with id: "+id);
        return journeyDriverService.deleteJourney(id);
    }

    @RequestMapping("journey/driver/edit")
    @ResponseBody
    public JourneyDriverBaseResponse editJourney(@RequestBody EditJourneyDriverRequest request){
        logger.info("editing driver journey with id "+request.getId());
        return journeyDriverService.editDetails(request);
    }
    //passenger

}