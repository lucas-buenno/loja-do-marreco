package com.quack.loja_do_marreco.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class CustomerDoNotExistsException extends QuackException {

    private final String detail;

    public CustomerDoNotExistsException(String detail) {
        super(detail);
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        problemDetail.setTitle("Não é possível seguir com a operação");
        problemDetail.setDetail(detail);
        return problemDetail;
    }
}
