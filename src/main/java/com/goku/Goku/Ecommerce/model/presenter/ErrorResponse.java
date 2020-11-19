package com.goku.goku.ecommerce.model.presenter;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ErrorResponse {

    private String message;
}
