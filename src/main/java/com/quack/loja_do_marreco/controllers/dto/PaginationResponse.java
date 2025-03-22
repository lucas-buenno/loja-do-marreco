package com.quack.loja_do_marreco.controllers.dto;

public record PaginationResponse(int pageNumber,
                                 int pageSize,
                                 long totalElements,
                                 int totalPages) {
}
