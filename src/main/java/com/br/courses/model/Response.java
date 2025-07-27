package com.br.courses.model;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {

    private String message;
    private List<T> data = new ArrayList<>();
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
