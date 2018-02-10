package com.taxikar.service;

import com.taxikar.bean.BaseResponse;
import com.taxikar.bean.request.JourneyPassenger;
import com.taxikar.entity.JurneyPassenger;
import com.taxikar.repository.JurneyPassengerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by parinaz on 2/10/2018.
 */
@Service
public class JourneyPassengerService {
    Logger logger = LoggerFactory.getLogger(JourneyPassengerService.class);
    private JurneyPassengerRepository jurneyPassengerRepository;

    @Autowired
    public JourneyPassengerService(JurneyPassengerRepository jurneyPassengerRepository){
        this.jurneyPassengerRepository = jurneyPassengerRepository;
    }

    public BaseResponse addJourney(JourneyPassenger request){
        //just add
        //nothing checked
        JurneyPassenger jurneyPassenger = new JurneyPassenger(request.getUserId(), request.getStartPos(), request.getEndPos(),
                request.getSeatNumber(), request.getStartTime(), request.getEndTime());
        jurneyPassengerRepository.save(jurneyPassenger);
        return new BaseResponse(1,"no error");
    }

    public BaseResponse deleteJourney(String id){
        JurneyPassenger jurneyPassenger = jurneyPassengerRepository.getOneWithId(id);
        if(jurneyPassenger==null) return new BaseResponse(0,"A jurney with this id does not exist");
        else jurneyPassengerRepository.delete(jurneyPassenger);
        return new BaseResponse(1,"No error");
    }

    /*public BaseResponse editJourney(){

    }*/
}
