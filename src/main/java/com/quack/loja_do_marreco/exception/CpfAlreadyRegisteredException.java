package com.quack.loja_do_marreco.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class CpfAlreadyRegisteredException extends QuackException {

    private final String detail;

    public CpfAlreadyRegisteredException(String detail) {
        super(detail);
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Não é possível registrar o vendedor");
        problemDetail.setDetail(detail);

        return problemDetail;
    }
}
