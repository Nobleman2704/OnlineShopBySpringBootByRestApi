package uz.jk.onlineshopbyspringboot.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.jk.onlineshopbyspringboot.domain.exception.*;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataAlreadyException.class)
    public ResponseEntity<BaseExceptionResponse> dataAlreadyException(DataAlreadyException e){
        return ResponseEntity.badRequest().body(
                new BaseExceptionResponse(400, e.getMessage())
        );
    }
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<BaseExceptionResponse> invalidInputException(InvalidInputException e){
        return ResponseEntity.badRequest().body(new BaseExceptionResponse(400, e.getMessage()));
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<BaseExceptionResponse> dataNotFoundException(DataNotFoundException e){
        return ResponseEntity.badRequest().body(
                new BaseExceptionResponse(404, e.getMessage())
        );
    }

    @ExceptionHandler(DataNotMatchedException.class)
    public ResponseEntity<BaseExceptionResponse> dataNotMatchedException(DataNotMatchedException e){
        return ResponseEntity.badRequest().body(
                new BaseExceptionResponse(404, e.getMessage())
        );
    }
}
