package roomescape.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> dtoException(MethodArgumentNotValidException e) {
        List<String> list = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            list.add(errorMessage);
        });

        return ResponseEntity.badRequest().body(list);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        if (e.getCause() instanceof MismatchedInputException mismatchedInputException) {
            final String errorMessage = mismatchedInputException.getPath().get(0).getFieldName() + " 필드의 값이 잘못되었습니다.";
            return ResponseEntity.badRequest().body(errorMessage);
        } else {
            return ResponseEntity.badRequest().body("확인할 수 없는 데이터가 들어왔습니다.");
        }
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<String> IllegalAccessException(IllegalAccessException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
