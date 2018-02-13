package com.taxikar.controllers;

import com.taxikar.bean.request.EditJourneyDriverRequest;
import com.taxikar.bean.request.JurneyDriverRequest;
import com.taxikar.bean.BaseResponse;
import com.taxikar.entity.JurneyDriver;
import com.taxikar.service.JourneyDriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(value = "/journey/driver/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse addJourneyDriver(@RequestBody JurneyDriverRequest request){
        logger.info("adding a journey from driver to database");
        return journeyDriverService.addNewJourney(request);
    }

    @RequestMapping(value = "/journey/driver/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse deleteJourney(@RequestBody String id){
        logger.info("deleting journey with id: "+id);
        return journeyDriverService.deleteJourney(id);
    }

    @RequestMapping(value = "/journey/driver/edit", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse editJourney(@RequestBody EditJourneyDriverRequest request){
        logger.info("editing driver journey with id "+request.getId());
        return journeyDriverService.editDetails(request);
    }

    @RequestMapping(value = "/journey/driver/activeTrips", method = RequestMethod.POST)
    @ResponseBody
    public List<JurneyDriver> getDriverActive(@RequestParam String id){
        logger.info("getting active journey for driver with user id "+id);
        return journeyDriverService.getAllActive(id);
    }

    @RequestMapping(value = "/journey/driver/pastTrips", method = RequestMethod.POST)
    @ResponseBody
    public List<JurneyDriver> getPasstTrips(@RequestParam String id){
        logger.info("getting past trips for driver with user id "+id);
        return journeyDriverService.getAllPast(id);
    }
}