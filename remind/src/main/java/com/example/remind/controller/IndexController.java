package com.example.remind.controller;

import java.util.Date;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.remind.exception.ErrorDetails;

@RestController
public class IndexController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public ResponseEntity<?> error() {
    	
    	ErrorDetails errorDetails = new ErrorDetails(new Date(), "지원하지 않는 URL입니다", "지원하지 않는 URL입니다");
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    public String getErrorPath() {
        return PATH;
    }
}