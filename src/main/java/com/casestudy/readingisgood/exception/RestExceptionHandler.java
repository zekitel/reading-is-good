package com.casestudy.readingisgood.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(DbNotFoundException.class)
    protected ResponseEntity<Object> dbNotFoundException(DbNotFoundException ex) {
        ServerErrorModel serverErrorModel = new ServerErrorModel(HttpStatus.NOT_FOUND);
        serverErrorModel.setMessage(ex.getMessage());
        return buildResponseEntity(serverErrorModel);
    }

    @ExceptionHandler({StartDateIsGreaterThanEndDateException.class})
    protected ResponseEntity<Object> startDateIsGreaterThanEndDateException(StartDateIsGreaterThanEndDateException ex) {
        ServerErrorModel serverErrorModel = new ServerErrorModel(HttpStatus.BAD_REQUEST);
        serverErrorModel.setMessage(ex.getMessage());
        return buildResponseEntity(serverErrorModel);
    }

    @ExceptionHandler({ResourceAlreadyExistsException.class})
    protected ResponseEntity<Object> resourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        ServerErrorModel serverErrorModel = new ServerErrorModel(HttpStatus.BAD_REQUEST);
        serverErrorModel.setMessage(ex.getMessage());
        return buildResponseEntity(serverErrorModel);
    }

    @ExceptionHandler({StockIsNotSufficientException.class})
    protected ResponseEntity<Object> stockIsNotSufficientException(StockIsNotSufficientException ex) {
        ServerErrorModel serverErrorModel = new ServerErrorModel(HttpStatus.BAD_REQUEST);
        serverErrorModel.setMessage(ex.getMessage());
        return buildResponseEntity(serverErrorModel);
    }

    @ExceptionHandler({StockValueChangedException.class})
    protected ResponseEntity<Object> stockValueChangedException(StockValueChangedException ex) {
        ServerErrorModel serverErrorModel = new ServerErrorModel(HttpStatus.resolve(StockValueChangedException.STATUS_CODE));
        serverErrorModel.setMessage(ex.getMessage());
        return buildResponseEntity(serverErrorModel);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    protected ResponseEntity<Object> violationOfUniqueFields(DataIntegrityViolationException ex) {
        ServerErrorModel serverErrorModel = new ServerErrorModel(HttpStatus.INTERNAL_SERVER_ERROR);
        serverErrorModel.setMessage(ex.getMessage());
        return buildResponseEntity(serverErrorModel);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ServerErrorModel serverErrorModel = new ServerErrorModel(HttpStatus.BAD_REQUEST);
        serverErrorModel.setMessage(ex.getMessage());
        serverErrorModel.setFieldErrorList(ex.getBindingResult().getFieldErrors());

        return buildResponseEntity(serverErrorModel);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        ServerErrorModel serverErrorModel = new ServerErrorModel(HttpStatus.BAD_REQUEST);
        serverErrorModel.setMessage(ex.getMessage());
        serverErrorModel.setConstraintViolationSet(ex.getConstraintViolations());

        return buildResponseEntity(serverErrorModel);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ServerErrorModel(HttpStatus.BAD_REQUEST, error));
    }


    private ResponseEntity<Object> buildResponseEntity(ServerErrorModel serverErrorModel) {
        return new ResponseEntity<>(serverErrorModel, serverErrorModel.getStatus());
    }

}