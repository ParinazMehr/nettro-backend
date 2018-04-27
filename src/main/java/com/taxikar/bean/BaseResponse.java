package com.taxikar.bean;

/**
 * Created by parinaz on 2/9/2018.
 */
public class BaseResponse {



    private String userID;
    private int status;
    private int errorCode;
    private String errorMessage;
    

    public BaseResponse(){}
    public BaseResponse(int status, String errorMessage){
        this.status = status;
        this.errorMessage = errorMessage;
    }
    public BaseResponse(int status, int errorCode, String errorMessage,String userID){
        this.userID=userID;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.status = status;
    }
    public String getUserID()
    {
        return userID;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }
    public int getStatus(){return status;}
    public void setStatus(int status){this.status = status;}

    public String getErrorMessage(){return errorMessage;}
    public void setErrorMessage(String errorMessage){this.errorMessage = errorMessage;}

    public int getErrorCode(){return errorCode;}
    public void setErrorCode(int errorCode){this.errorCode = errorCode;}
}
