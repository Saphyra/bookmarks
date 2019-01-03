package bookmarks.auth;

import com.github.saphyra.authservice.PropertySource;
import com.github.saphyra.authservice.domain.Role;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static bookmarks.controller.PageController.INDEX_MAPPING;

@Component
public class PropertySourceImpl implements PropertySource {
    public static final String COOKIE_ACCESS_TOKEN = "accesstoken";
    public static final String COOKIE_USER_ID = "userid";

    private static final String REQUEST_TYPE_HEADER = "Request-Type";
    private static final String REST_TYPE_REQUEST = "rest";

    private static final List<String> ALLOWED_URIS = Arrays.asList(
        "/",
        "/**/favicon.ico",
        "/login",
        "/user/register",
        "/user/name/exist",
        "/user/email/exist",
        "/css/**",
        "/js/**"
    );
    private static final long TOKEN_EXPIRATION_MINUTES = 8 * 60;

    @Override
    public String getRequestTypeHeader() {
        return REQUEST_TYPE_HEADER;
    }

    @Override
    public String getRestTypeValue() {
        return REST_TYPE_REQUEST;
    }

    @Override
    public String getUnauthorizedRedirection() {
        return INDEX_MAPPING;
    }

    @Override
    public String getForbiddenRedirection() {
        return INDEX_MAPPING;
    }

    @Override
    public String getAccessTokenIdCookie() {
        return COOKIE_ACCESS_TOKEN;
    }

    @Override
    public String getUserIdCookie() {
        return COOKIE_USER_ID;
    }

    @Override
    public List<String> getAllowedUris() {
        return ALLOWED_URIS;
    }

    @Override
    public Map<String, Set<Role>> getRoleSettings() {
        return new HashMap<>();
    }

    @Override
    public boolean isMultipleLoginAllowed() {
        return true;
    }

    @Override
    public long getTokenExpirationMinutes() {
        return TOKEN_EXPIRATION_MINUTES;
    }

    @Override
    public int getFilterOrder() {
        return 0;
    }
}
