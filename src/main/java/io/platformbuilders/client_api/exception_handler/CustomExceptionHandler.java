package io.platformbuilders.client_api.exception_handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(EntityExistsException.class)
    public ResponseError handle(EntityExistsException ex) {
        ex.printStackTrace();
        return new ResponseError(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseError handle(EntityNotFoundException ex) {
        ex.printStackTrace();
        return new ResponseError(ex.getMessage());
    }

}
