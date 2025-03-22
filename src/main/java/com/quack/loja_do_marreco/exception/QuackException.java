package com.quack.loja_do_marreco.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class QuackException extends RuntimeException {

    public QuackException(String detail) {
    }

    public ProblemDetail toProblemDetail(){
        var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Duck Duck Duck");
        return pd;
    }
}
