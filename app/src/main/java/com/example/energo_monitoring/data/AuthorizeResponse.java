package com.example.energo_monitoring.data;

public class AuthorizeResponse {
    private boolean success;
    private UserData user;
    private String error = "";

    public AuthorizeResponse(boolean success, UserData user) {
        this.success = success;
        this.user = user;
    }

    public AuthorizeResponse(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UserData getUser() {
        return user;
    }

    public String getError() {
        return error;
    }
}
