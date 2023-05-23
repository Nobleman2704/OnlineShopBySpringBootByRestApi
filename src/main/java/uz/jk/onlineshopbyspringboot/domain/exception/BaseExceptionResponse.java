package uz.jk.onlineshopbyspringboot.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseExceptionResponse {
    private int status;
    private String message;
}
