package com.goku.goku.ecommerce.model.builder;

import com.goku.goku.ecommerce.model.presenter.ErrorResponse;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ErrorResponseBuilder {

    private String message;
    public ErrorResponseBuilder message(String message) {
        this.message = message;
        return this;
    }

    public ErrorResponse build() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        return errorResponse;
    }
}
