package com.taxikar.bean.request;

/**
 * Created by parinaz on 4/13/2018.
 */
public class DeleteJourneyRequestBean {
    private String id;
    private String userId;

    public DeleteJourneyRequestBean(){}
    public DeleteJourneyRequestBean(String id, String userId){
        this.id = id;
        this.userId = userId;
    }

    public String getId(){return id;}
    public void setId(String id){this.id = id;}

    public String getUserId(){return userId;}
    public void setUserId(String userId){this.userId = userId;}
}
