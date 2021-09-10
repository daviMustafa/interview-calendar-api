package com.tamanna.handlers;

public class ErrorObject {

    private String errorDescription;

    public ErrorObject(String description) {
        super();
        this.errorDescription = description;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
