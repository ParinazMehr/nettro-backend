package com.taxikar.service;

import com.taxikar.bean.BaseResponse;
import com.taxikar.bean.request.EditJourneyPassengerRequest;
import com.taxikar.bean.request.JourneyPassenger;
import com.taxikar.entity.JurneyPassenger;
import com.taxikar.repository.JurneyDriverRepository;
import com.taxikar.repository.JurneyPassengerRepository;
import com.taxikar.repository.UserRepository;
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
    private UserRepository userRepository;
    private JurneyDriverRepository jurneyDriverRepository;

    @Autowired
    public JourneyPassengerService(JurneyPassengerRepository jurneyPassengerRepository, UserRepository userRepository, JurneyDriverRepository jurneyDriverRepository){
        this.jurneyPassengerRepository = jurneyPassengerRepository;
        this.userRepository = userRepository;
        this.jurneyDriverRepository = jurneyDriverRepository;
    }

    public BaseResponse addJourney(JourneyPassenger request){
        //check the user with this user id exist
        boolean existance = userRepository.ExistanceByUserId(request.getUserId());
        if(existance==false) return new BaseResponse(0, "User with this user id does not exist");
        //start end pose limitation?!?!?!
        //seat number limitation
        if(request.getSeatNumber()==0 || request.getSeatNumber()>4) return new BaseResponse(0, "Number of requested seats can not be zero or greater than 4");
        //check that user does not have any trips as a passenger or driver between start and end of given time
        boolean jr = jurneyDriverRepository.journeyExistInTime(request.getUserId(), request.getStartTime());
        if(jr==true) return new BaseResponse(0, "the user already has a trip in this time");

        JurneyPassenger jurneyPassenger = new JurneyPassenger(request.getUserId(), request.getStartPos(), request.getEndPos(),
                request.getSeatNumber(), request.getStartTime(), request.getEndTime());
        jurneyPassengerRepository.save(jurneyPassenger);
        return new BaseResponse(1,"no error, journey for passenger with user id "+request.getUserId()+" has been successfully recorded.");
    }

    public BaseResponse deleteJourney(String id, String userId){
        //only the passenger that requested can delete the journey
        JurneyPassenger jurneyPassenger = jurneyPassengerRepository.getOneWithId(id);
        if(jurneyPassenger==null) return new BaseResponse(0,"A jurney with this id does not exist");
        if(jurneyPassenger.getUserId()!=userId) return new BaseResponse(0,"The user does not have permission to delete this journey");
        //no restriction I cant think of
        else jurneyPassengerRepository.delete(jurneyPassenger);
        return new BaseResponse(1,"No error");
    }

    public BaseResponse editJourney(EditJourneyPassengerRequest request){
        JurneyPassenger jurneyPassenger = jurneyPassengerRepository.getOneWithId(request.getId());
        if(jurneyPassenger==null) return new BaseResponse(0,"no journey with this request id exists");
        if(request.getUserId()!= jurneyPassenger.getUserId()) return new BaseResponse(0,"The user does not have permission to edit this journey");
        //no restriction that I can think of, maybe on time?!?!?
        jurneyPassenger.setStartPos(request.getStartPos());
        jurneyPassenger.setEndPos(request.getEndPos());
        jurneyPassenger.setMaxPrice(request.getMaxPrice());
        jurneyPassenger.setStartTime(request.getStartTime());
        jurneyPassenger.setEndTime(request.getEndTime());
        jurneyPassenger.setSeatNumber(request.getSeatNumber());
        jurneyPassenger.setStatus(request.getStatus());
        jurneyPassenger.setRank(request.getRank());
        jurneyPassengerRepository.save(jurneyPassenger);
        return new BaseResponse(1, "No error, Journey with id "+ request.getId()+" has been edited successfully");
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