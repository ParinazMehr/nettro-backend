package com.taxikar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by parinaz on 2/9/2018.
 */
@Entity
@Table(name = "jurney_passenger")
public class JurneyPassenger {
    @Id
    private String id;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "START_POS")
    private String startPos;
    @Column(name = "END_POS")
    private String endPos;
    @Column(name = "SEAT_NUMBER")
    private int seatNumber;
    @Column(name = "MAX_PRICE")
    private Long maxPrice;
    @Column(name = "START_TIME")
    private Timestamp startTime;
    @Column(name = "END_TIME")
    private Timestamp endTime;
    @Column(name = "STATUS")
    private int status;
    @Column(name = "RANK")
    private int rank;

    public JurneyPassenger(){}
    public JurneyPassenger(String userId, String startPos, String endPos, int seatNumber, Timestamp startTime, Timestamp endTime){
        this.userId = userId;
        this.startPos = startPos;
        this.endPos = endPos;
        this.seatNumber = seatNumber;
        this.startTime = startTime;
        this.endTime = endTime;

    }

    public String getId(){return id;}
    public void setId(String id){this.id = id;}

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
    public void setEndTime(Timestamp endTime){this.endTime = endTime;}

    public int getStatus(){return status;}
    public void setStatus(int status){this.status = status;}

    public int getRank(){return rank;}
    public void setRank(int rank){this.rank = rank;}
}
