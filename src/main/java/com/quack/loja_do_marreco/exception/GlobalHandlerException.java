package com.quack.loja_do_marreco.exception;

import com.quack.loja_do_marreco.exception.dto.InvalidParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalHandlerException {


    @ExceptionHandler(QuackException.class)
    public ProblemDetail handlerQuackException(QuackException e) {
        return e.toProblemDetail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {


        List<InvalidParam> invalidParams = e.getFieldErrors().stream()
                .map(fieldError ->
                        new InvalidParam(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();


        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        problemDetail.setTitle("Verifique os campos informados na requisição");
        problemDetail.setProperty("Campos com erro", invalidParams);

        return problemDetail;
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handlerHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        problemDetail.setTitle("Verifique os campos informados na requisição");
        problemDetail.setDetail(e.getMessage());

        return problemDetail;
    }

}
