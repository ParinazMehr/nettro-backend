package com.taxikar.service;

import com.taxikar.bean.request.EditJourneyDriverRequest;
import com.taxikar.bean.request.JurneyDriverRequest;
import com.taxikar.bean.BaseResponse;
import com.taxikar.entity.DriverDetail;
import com.taxikar.entity.JurneyDriver;
import com.taxikar.entity.Users;
import com.taxikar.repository.JurneyDriverRepository;
import com.taxikar.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by parinaz on 2/9/2018.
 */
@Service
public class JourneyDriverService {
    Logger logger = LoggerFactory.getLogger(JourneyDriverService.class);
    private JurneyDriverRepository jurneyDriverRepository;
    private UserRepository userRepository;

    @Autowired
    public JourneyDriverService(JurneyDriverRepository jurneyDriverRepository, UserRepository userRepository){
        this.jurneyDriverRepository = jurneyDriverRepository;
        this.userRepository = userRepository;
    }

    public BaseResponse addNewJourney(JurneyDriverRequest request){
        //check input parameters
        Users user = userRepository.getOne(request.getUserId());
        //check user id exists and is validated as a driver(status 1 is verified, 0 is pending and -1 is rejected)
        boolean existance = userRepository.ExistanceByUserId(request.getUserId());
        if(existance==false || user.getDriverDetail()==null || user.getDriverDetail().getStatus()!=1) return new BaseResponse(0, "user does not exist or is not a valid driver");
        //check seat number to not be zero and smaller than a defined max or based on car type
        if(request.getSeatNumber()==0 || request.getSeatNumber()>4) return new BaseResponse(0,"Number of available seats can not be zero or greater than 4");
        //start pose and end pose limitation??????
        //check this driver does not have a trip in the input time?!?!
        boolean jr = jurneyDriverRepository.journeyExistInTime(request.getUserId(), request.getStartTime());
        if(jr== false){
            //there is no trip for the driver in this time
            //add to repository
            JurneyDriver jurneyDriver = new JurneyDriver(request.getUserId(), request.getStartPos(), request.getEndPos(), request.getStartTime(), request.getSeatNumber());
            //set the price based on formula
            jurneyDriver.setPrice(10000L);
            jurneyDriverRepository.save(jurneyDriver);
            return new BaseResponse(1, "no error, journey for driver with userId "+request.getUserId()+" has been successfully created");
        }
        else logger.error("there is already a trip in this time for the driver");
        return new BaseResponse(0, "You already have a trip in this time");
    }

    public BaseResponse deleteJourney(String id, String userId){
        //check the user that wants to delete the journey is the driver of the same journey, others dont have permission to delete
        if(jurneyDriverRepository.getOneJourney(id).getUserId()!= userId) return new BaseResponse(0,"the user with id "+ userId+" does not have permission to delete this journey");
        //find the journey remove, if not exist return an error
        JurneyDriver jurneyDriver = jurneyDriverRepository.getOneJourney(id);
        if(jurneyDriver==null){
            logger.error("Jurney with id "+id+" does not exist");
            return new BaseResponse(0, "journey with id "+id+" does not exist");
        }else
            //if the journey exists, the passengers that have been registered for the trip should be notified
            //driver should be given negative rank?!?!
            //driver should not be able to delete a trip before a certain amount of start
            jurneyDriverRepository.delete(jurneyDriver);
            return new BaseResponse(1, "no error");
    }

    public BaseResponse editDetails(EditJourneyDriverRequest request){
        //edit in journey should not be allowed certain amount of time before the start of journey
        JurneyDriver jurneyDriver = jurneyDriverRepository.getOneJourney(request.getId());
        //user id should not change since the driver can not be changed, but ut should be checked that it is the same as the one in the table since
        //only the driver can change the journey not others
        if(jurneyDriver.getUserId()!= request.getUserId()) return new BaseResponse(0,"Only the driver can change the journey");
        //pose should not change or can change with restriction?!?!?
        jurneyDriver.setStartPos(request.getStartPos());
        jurneyDriver.setEndPos(request.getEndPos());
        //if changes a notification should be send to passenger of change or should not be able to change
        jurneyDriver.setStartTime(request.getStartTime());
        //seat number should not be zero or smaller than number of passengers already registered for this trip
        if(request.getSeatNumber()==0 || request.getSeatNumber()<jurneyDriver.getSeatNumber()){
            return new BaseResponse(0, "The seat number is zero or less than number of passengers already assigned to the trip.");
        }
        //if time and pos changes should notify the passengers and remove their trip
        jurneyDriver.setSeatNumber(request.getSeatNumber());
        jurneyDriver.setEvent(request.getEvent());
        jurneyDriver.setDescription(request.getDescription());
        jurneyDriverRepository.save(jurneyDriver);
        return new BaseResponse(1,"no error");
    }

    public List<JurneyDriver> getAllActive(String id){
        Timestamp time = new Timestamp(System.currentTimeMillis());
        List<JurneyDriver> driverList = jurneyDriverRepository.getAllActive(id, time);
        return driverList;
    }

    public List<JurneyDriver> getAllPast(String id){
        Timestamp time = new Timestamp(System.currentTimeMillis());
        List<JurneyDriver> driverList = jurneyDriverRepository.getAllPast(id, time);
        return driverList;
    }
}
