package com.taxikar.bean.request;

import java.sql.Timestamp;

/**
 * Created by parinaz on 2/11/2018.
 */
public class EditJourneyPassengerRequest {
    private String id;
    private String userId;
    private String startPos;
    private String endPos;
    private int seatNumber;
    private Long maxPrice;
    private Timestamp startTime;
    private Timestamp endTime;
    private int status;        ///dont know  if the user can change it or not
    private int rank;

    public String getId(){return id;}
    public void setId(String id){this.id= id;}

    public String getUserId(){return userId;}
    public void setUserId(String userId){this.userId = userId;}

    public String getStartPos(){return startPos;}
    public void setStartPos(String startPos){this.startPos = startPos;}

    public String getEndPos(){return endPos;}
    public void setEndPos(String endPos){this.endPos = endPos;}

    public int getSeatNumber(){return seatNumber;}
    public void setSeatNumber(int seatNumber){this.seatNumber = seatNumber;}

    public Long getMaxPrice(){return maxPrice;}
    public void setMaxPrice(Long maxPrice){this.maxPrice = maxPrice;}

    public Timestamp getStartTime(){return startTime;}
    public void setStartTime(Timestamp startTime){this.startTime = startTime;}

    public Timestamp getEndTime(){return endTime;}
    public void setEndTime(Timestamp  endTime){this.endTime = endTime;}

    public int getStatus(){return status;}
    public void setStatus(int status){this.status = status;}

    public int getRank(){return rank;}
    public void setRank(int rank){this.rank = rank;}
}
