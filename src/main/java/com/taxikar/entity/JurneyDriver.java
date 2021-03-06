package com.taxikar.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by parinaz on 2/8/2018.
 */
@Entity
@Table(name = "jurney_driver")
public class JurneyDriver {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(columnDefinition = "char(36)")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "START_POS")
    private String startPos;
    @Column(name = "END_POS")
    private String endPos;
    @Column(name = "START_TIME")
    private Timestamp startTime;
    @Column(name = "SEAT_NUMBER")
    private int seatNumber;
    @Column(name = "PRICE")
    private Long price;
    @Column(name = "EVENT")
    private Long event;
    @Column(name = "DESCRIPTION")
    private String description;

    public JurneyDriver(){}
    public JurneyDriver(String userId, String startPos, String  endPos, Timestamp startTime, int seatNumber){
        this.userId = userId;
        this.startPos = startPos;
        this.endPos = endPos;
        this.startTime = startTime;
        this.seatNumber = seatNumber;
    }

    public String getId(){return id;}
    public void setId(String id){this.id = id;}

    public String getUserId(){return userId;}
    public void setUserId(String userId){this.userId = userId;}

    public String getStartPos(){return startPos;}
    public void setStartPos(String startPos){this.startPos = startPos;}

    public String getEndPos(){return endPos;}
    public void setEndPos(String endPos){this.endPos = endPos;}

    public Timestamp getStartTime(){return startTime;}
    public void setStartTime(Timestamp startTime){this.startTime = startTime;}

    public int getSeatNumber(){return seatNumber;}
    public void setSeatNumber(int seatNumber){}

    public Long getPrice(){return price;}
    public void setPrice(Long price){this.price = price;}

    public Long getEvent(){return event;}
    public void setEvent(Long event){this.event = event;}

    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}
}
