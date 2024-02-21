package com.mayank.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
@Data
@NoArgsConstructor
public class CustomResponse {
    private String message;
    private String timeStamp;
    private HttpStatus status;

    public CustomResponse(String message, HttpStatus status) {
        this.message = message;
        this.timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        this.status = status;
    }
}
