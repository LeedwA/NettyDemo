package com.example.user.nettydemo.protosuffTest;

/*************************************
 功能：
 *************************************/

public class EchoRequest {
    private String requestId;
    private Object requestObj;
    private Class<?> requestObjClass;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getRequestObj() {
        return requestObj;
    }

    public void setRequestObj(Object requestObj) {
        this.requestObj = requestObj;
    }

    public Class<?> getRequestObjClass() {
        return requestObjClass;
    }

    public void setRequestObjClass(Class<?> requestObjClass) {
        this.requestObjClass = requestObjClass;
    }

}
