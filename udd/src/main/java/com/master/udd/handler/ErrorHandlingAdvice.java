package com.master.udd.handler;

import com.master.udd.exception.EntityNotFoundException;
import com.master.udd.exception.InvalidAddressException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Date;

@ControllerAdvice
public class ErrorHandlingAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ErrorResponse onEntityNotFoundException(EntityNotFoundException e) {
        return new ErrorResponse(new Date(), e.getMessage());
    }

    @ExceptionHandler(InvalidAddressException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse onInvalidAddressException(InvalidAddressException e) {
        return new ErrorResponse(new Date(), e.getMessage());
    }

}
