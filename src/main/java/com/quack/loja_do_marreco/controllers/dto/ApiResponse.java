package com.quack.loja_do_marreco.controllers.dto;

import java.util.List;

public record ApiResponse<T>(List<T> content,
                             PaginationResponse paginationResponse) {
}
