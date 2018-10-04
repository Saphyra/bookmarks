package bookmarks.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ExceptionBase {
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
