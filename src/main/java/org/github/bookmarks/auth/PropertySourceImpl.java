package org.github.bookmarks.auth;

import static org.github.bookmarks.common.controller.PageController.INDEX_MAPPING;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.github.saphyra.authservice.PropertySource;
import com.github.saphyra.authservice.domain.AllowedUri;
import com.github.saphyra.authservice.domain.RoleSetting;

@Component
public class PropertySourceImpl implements PropertySource {
    private static final String COOKIE_ACCESS_TOKEN = "bookmarks_access_token";
    public static final String COOKIE_USER_ID = "bookmarks_user_id";

    private static final String REQUEST_TYPE_HEADER = "Request-Type";
    private static final String REST_TYPE_REQUEST = "rest";

    private static final long TOKEN_EXPIRATION_MINUTES = 8 * 60; // 8 hours

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
    public List<AllowedUri> getAllowedUris() {
        return Arrays.asList(
            new AllowedUri("/", HttpMethod.GET),
            new AllowedUri("/**/favicon.ico", HttpMethod.GET),
            new AllowedUri("/user/register", HttpMethod.POST),
            new AllowedUri("/user/name/exist", HttpMethod.POST),
            new AllowedUri("/user/email/exist", HttpMethod.POST),
            new AllowedUri("/css/**", HttpMethod.GET),
            new AllowedUri("/js/**", HttpMethod.GET)
        );
    }

    @Override
    public Set<RoleSetting> getRoleSettings() {
        return new HashSet<>();
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

    @Override
    public String getSuccessfulLoginRedirection() {
        return "links";
    }

    @Override
    public Optional<String> getLogoutRedirection() {
        return Optional.empty();
    }
}
