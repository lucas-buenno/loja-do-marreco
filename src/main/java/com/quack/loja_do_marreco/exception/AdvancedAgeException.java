package com.quack.loja_do_marreco.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class AdvancedAgeException extends QuackException {

    private final String detail;

    public AdvancedAgeException(String detail) {
        super(detail);
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        problemDetail.setTitle("O pato está muito velho para ser registrado.");
        problemDetail.setDetail(detail);

        return problemDetail;
    }
}
