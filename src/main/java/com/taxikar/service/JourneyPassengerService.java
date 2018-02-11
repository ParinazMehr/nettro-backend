package com.taxikar.service;

import com.taxikar.bean.BaseResponse;
import com.taxikar.bean.request.EditJourneyPassengerRequest;
import com.taxikar.bean.request.JourneyPassenger;
import com.taxikar.entity.JurneyPassenger;
import com.taxikar.repository.JurneyPassengerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
        return new BaseResponse(1,"No error");
    }

    public BaseResponse deleteJourney(String id){
        JurneyPassenger jurneyPassenger = jurneyPassengerRepository.getOneWithId(id);
        if(jurneyPassenger==null) return new BaseResponse(0,"A jurney with this id does not exist");
        else jurneyPassengerRepository.delete(jurneyPassenger);
        return new BaseResponse(1,"No error");
    }

    public BaseResponse editJourney(EditJourneyPassengerRequest request){
        JurneyPassenger jurneyPassenger = jurneyPassengerRepository.getOneWithId(request.getId());
        if(jurneyPassenger==null) return new BaseResponse(0,"no journey with this request id exists");
        else {
            jurneyPassenger.setStartPos(request.getStartPos());
            jurneyPassenger.setEndPos(request.getEndPos());
            jurneyPassenger.setMaxPrice(request.getMaxPrice());
            jurneyPassenger.setStartTime(request.getStartTime());
            jurneyPassenger.setEndTime(request.getEndTime());
            jurneyPassenger.setSeatNumber(request.getSeatNumber());
            jurneyPassenger.setStatus(request.getStatus());
            jurneyPassenger.setRank(request.getRank());
            jurneyPassengerRepository.save(jurneyPassenger);
            return new BaseResponse(1, "No error");
        }
    }

    public List<JurneyPassenger> getActiveTrips(String id){
        //get the time
        Timestamp time = new Timestamp( System.currentTimeMillis());
        //get the list from db
        List<JurneyPassenger> list = jurneyPassengerRepository.getAllActive(id,time);
        //analys and send it back
        return list;
    }

    public List<JurneyPassenger> getPastTrips(String id){
        Timestamp time = new Timestamp(System.currentTimeMillis());
        List<JurneyPassenger> list = jurneyPassengerRepository.getAllPast(id, time);
        return list;
    }
}