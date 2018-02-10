package com.taxikar.bean.request;

import java.sql.Timestamp;

/**
 * Created by parinaz on 2/10/2018.
 */
public class EditJourneyDriverRequest {
    private String id;
    private String userId;
    private String startPos;
    private String endPos;
    private Timestamp startTime;
    private int seatNumber;
    private Long price;
    private Long event;
    private String description;

    public String getId(){return id;}
    public void setId(String id){this.id = id;}

    public String getUserId(){return userId;}
    public void setUserId(String userId){this.userId = userId;}

    public String getStartPos(){return startPos;}
    public void setStartPos(String startPos){this.startPos = startPos;}

    public String getEndPos(){return endPos;}
    public void setEndPos(String endPos){this.endPos=endPos;}

    public Timestamp getStartTime(){return startTime;}
    private void setStartTime(Timestamp startTime){this.startTime = startTime;}

    public int getSeatNumber(){return seatNumber;}
    public void setSeatNumber(int seatNumber){this.seatNumber = seatNumber;}

    public Long getPrice(){return price;}
    public void setPrice(Long price){this.price = price;}

    public Long getEvent(){return event;}
    public void setEvent(Long event){this.event = event;}

    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}
}
