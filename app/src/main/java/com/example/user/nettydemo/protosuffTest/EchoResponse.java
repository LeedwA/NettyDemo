package com.example.user.nettydemo.protosuffTest;

/*************************************
 功能：
 *************************************/

public class EchoResponse {
    private String responseId;
    private Object responseObj;
    private Class<?> responseObjClass;

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public Object getResponseObj() {
        return responseObj;
    }

    public void setResponseObj(Object responseObj) {
        this.responseObj = responseObj;
    }

    public Class<?> getResponseObjClass() {
        return responseObjClass;
    }

    public void setResponseObjClass(Class<?> responseObjClass) {
        this.responseObjClass = responseObjClass;
    }
}
