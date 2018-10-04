package bookmarks.common.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ExceptionBase {
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
