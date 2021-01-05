package com.example.demo;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.Map;
@Getter
public class CustomHttpMessageNotReadableException extends HttpMessageNotReadableException {
    Map<?,?> reqData;
     public CustomHttpMessageNotReadableException(Map<?,?> reqData, String msg, Throwable cause, HttpInputMessage httpInputMessage) {
        super(msg, cause, httpInputMessage);
        this.reqData=reqData;
    }
}
