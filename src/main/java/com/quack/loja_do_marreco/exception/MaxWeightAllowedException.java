package com.quack.loja_do_marreco.exception;

import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class MaxWeightAllowedException extends QuackException {

    private final String detail;

    public MaxWeightAllowedException(String detail) {
        super(detail);
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        problemDetail.setTitle("O pato está muito gordo");
        problemDetail.setDetail(detail);
        return problemDetail;
    }
}
