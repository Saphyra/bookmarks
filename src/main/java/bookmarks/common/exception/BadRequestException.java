package bookmarks.common.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ExceptionBase {
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
