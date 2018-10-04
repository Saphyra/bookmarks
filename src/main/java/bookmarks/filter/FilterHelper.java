package bookmarks.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FilterHelper {
    public static final String COOKIE_ACCESS_TOKEN = "accesstoken";
    public static final String COOKIE_USER_ID = "userid";

    static final String REQUEST_TYPE_HEADER = "Request-Type";
    static final String REST_TYPE_REQUEST = "rest";

    public void handleUnauthorized(HttpServletRequest request, HttpServletResponse response, String redirection) throws IOException {
        if (REST_TYPE_REQUEST.equals(request.getHeader(REQUEST_TYPE_HEADER))) {
            log.info("Sending error. Cause: Unauthorized access.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed.");
        } else {
            log.info("Redirect to {}. Cause: Unauthorized access.", redirection);
            response.sendRedirect(redirection);
        }
    }
}
