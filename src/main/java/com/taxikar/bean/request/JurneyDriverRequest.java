package com.taxikar.bean.request;

import java.sql.Timestamp;

/**
 * Created by parinaz on 2/9/2018.
 */
public class JurneyDriverRequest {
    private String userId;
    private String startPos;
    private String endPos;
    private Timestamp startTime;
    private int seatNumber;

    public JurneyDriverRequest(String userId, String startPos, String endPos, Timestamp startTime, int seatNumber){
        this.userId = userId;
        this.startPos = startPos;
        this.endPos = endPos;
        this.startTime = startTime;
        this.seatNumber = seatNumber;
    }

    public String getUserId(){return userId;}
    public void setUserId(String userId){this.userId = userId;}

    public String getStartPos(){return startPos;}
    public void setStartPos(String startPos){this.startPos = startPos;}

    public String getEndPos(){return endPos;}
    public void setEndPos(String  endPos){this.endPos = endPos;}

    public Timestamp getStartTime(){return startTime;}
    public void setStartTime(Timestamp startTime){this.startTime = startTime;}

    public int getSeatNumber(){return seatNumber;}
    public void setSeatNumber(int seatNumber){this.seatNumber = seatNumber;}
}
