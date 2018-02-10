package com.taxikar.service;

import com.taxikar.bean.request.EditJourneyDriverRequest;
import com.taxikar.bean.request.JurneyDriverRequest;
import com.taxikar.bean.BaseResponse;
import com.taxikar.entity.JurneyDriver;
import com.taxikar.repository.JurneyDriverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by parinaz on 2/9/2018.
 */
@Service
public class JourneyDriverService {
    Logger logger = LoggerFactory.getLogger(JourneyDriverService.class);
    private JurneyDriverRepository jurneyDriverRepository;

    @Autowired
    public JourneyDriverService(JurneyDriverRepository jurneyDriverRepository){
        this.jurneyDriverRepository = jurneyDriverRepository;
    }

    public BaseResponse addNewJourney(JurneyDriverRequest request){
        //check this driver does not have a trip in the input time
        List<JurneyDriver> jr = jurneyDriverRepository.getSpecificPrice(request.getUserId(), request.getStartTime());
        if(jr.size()==0){
            //there is no trip for the driver in this time
            //add to repository
            JurneyDriver jurneyDriver = new JurneyDriver(request.getUserId(), request.getStartPos(), request.getEndPos(), request.getStartTime(), request.getSeatNumber());
            jurneyDriverRepository.save(jurneyDriver);
            return new BaseResponse(1, "no error");
        }
        else logger.error("there is already a trip in this time for the driver");
        return new BaseResponse(0, "You already have a trip in this time");
    }

    public BaseResponse deleteJourney(String id){
        //find the journey remove, if not exist return an error
        JurneyDriver jurneyDriver = jurneyDriverRepository.getOneJourney(id);
        if(jurneyDriver==null){
            logger.error("Jurney with id "+id+" does not exist");
            return new BaseResponse(0, "journey with id "+id+" does not exist");
        }else
            jurneyDriverRepository.delete(jurneyDriver);
            return new BaseResponse(1, "no error");
    }

    public BaseResponse editDetails(EditJourneyDriverRequest request){
        JurneyDriver jurneyDriver = jurneyDriverRepository.getOneJourney(request.getId());
        jurneyDriver.setUserId(request.getUserId());
        jurneyDriver.setStartPos(request.getStartPos());
        jurneyDriver.setEndPos(request.getEndPos());
        jurneyDriver.setStartTime(request.getStartTime());
        jurneyDriver.setSeatNumber(request.getSeatNumber());
        jurneyDriver.setPrice(request.getPrice());
        jurneyDriver.setEvent(request.getEvent());
        jurneyDriver.setDescription(request.getDescription());
        jurneyDriverRepository.save(jurneyDriver);
        return new BaseResponse(1,"no error");
    }
}
