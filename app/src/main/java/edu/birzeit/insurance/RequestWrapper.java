package edu.birzeit.insurance;

public class RequestWrapper {

    String data;

    String signature;

    public RequestWrapper() {
    }

    public RequestWrapper(String data, String signature) {
        this.data = data;
        this.signature = signature;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
