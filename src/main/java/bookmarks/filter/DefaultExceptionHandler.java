package bookmarks.filter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import bookmarks.common.exception.ExceptionBase;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("unused")
@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(ExceptionBase.class)
    public ResponseEntity<String> handleSkyXpException(ExceptionBase e) {
        log.warn(e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), e.getResponseStatus());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handle(RuntimeException ex) {
        log.error("Internal Server Error: {}. Message: {}", ex.getClass().getName(), ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleAll(Exception ex) {
        log.error("Unknown Server Error: {}. Message: {}", ex.getClass().getName(), ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
