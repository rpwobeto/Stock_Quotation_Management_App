package com.stock.quotation.app.validation;

public class ErrorForm {

    private String error;

    public ErrorForm(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

}