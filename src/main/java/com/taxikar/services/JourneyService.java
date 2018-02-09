package com.taxikar.services;

import com.taxikar.bean.request.JurneyDriverRequest;
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
public class JourneyService {
    Logger logger = LoggerFactory.getLogger(JourneyService.class);
    private JurneyDriverRepository jurneyDriverRepository;

    @Autowired
    public JourneyService(JurneyDriverRepository jurneyDriverRepository){
        this.jurneyDriverRepository = jurneyDriverRepository;
    }

    public void addNewJourney(JurneyDriverRequest request){
        //check this driver does not have a trip in the input time
        List<JurneyDriver> jr = jurneyDriverRepository.getSpecificPrice(request.getUserId(), request.getStartTime());
        if(jr.size()==0){
            //there is no trip for the driver in this time
            //add to repository
            JurneyDriver jurneyDriver = new JurneyDriver(request.getUserId(), request.getStartPos(), request.getEndPos(), request.getStartTime(), request.getSeatNumber());
            jurneyDriverRepository.save(jurneyDriver);
        }
        else logger.error("there is already a trip in this time for the driver");
        return;
    }
}
