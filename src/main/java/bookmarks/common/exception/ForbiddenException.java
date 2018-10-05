package bookmarks.common.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ExceptionBase {
    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
