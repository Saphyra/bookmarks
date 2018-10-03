package bookmarks.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ExceptionBase extends RuntimeException {
    private final HttpStatus responseStatus;

    protected ExceptionBase(HttpStatus responseStatus, String message) {
        super(message);
        this.responseStatus = responseStatus;
    }
}
