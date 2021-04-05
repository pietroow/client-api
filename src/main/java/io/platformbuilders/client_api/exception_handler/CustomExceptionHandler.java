package io.platformbuilders.client_api.exception_handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseBody
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(EntityExistsException.class)
    public ResponseError handle(EntityExistsException ex) {
        ex.printStackTrace();
        return new ResponseError(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseError handle(EntityNotFoundException ex) {
        ex.printStackTrace();
        return new ResponseError(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String mensagem = messageSource.getMessage(Objects.requireNonNull(fieldError), LocaleContextHolder.getLocale());
        ResponseError erro = new ResponseError(mensagem.toUpperCase());
        return handleExceptionInternal(ex, erro, headers, BAD_REQUEST, request);
    }

}
