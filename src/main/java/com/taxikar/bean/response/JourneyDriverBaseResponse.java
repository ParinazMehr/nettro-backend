package com.taxikar.bean.response;

/**
 * Created by parinaz on 2/9/2018.
 */
public class JourneyDriverBaseResponse {
    private int status;
    private String errorMessage;

    public JourneyDriverBaseResponse(){}
    public JourneyDriverBaseResponse(int status, String errorMessage){
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public int getStatus(){return status;}
    public void setStatus(int status){this.status = status;}

    public String getErrorMessage(){return errorMessage;}
    public void setErrorMessage(String errorMessage){this.errorMessage = errorMessage;}
}
