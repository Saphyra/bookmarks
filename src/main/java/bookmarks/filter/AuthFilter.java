package bookmarks.filter;

import static bookmarks.filter.FilterHelper.COOKIE_ACCESS_TOKEN;
import static bookmarks.filter.FilterHelper.COOKIE_USER_ID;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import bookmarks.service.AuthenticationService;
import bookmarks.controller.PageController;
import bookmarks.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("NullableProblems")
@Slf4j
@RequiredArgsConstructor
@Component
public class AuthFilter extends OncePerRequestFilter {
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();
    private static final List<String> allowedUris = Arrays.asList(
        "/",
        "/**/favicon.ico",
        "/login",
        "/user/register",
        "/user/name/exist",
        "/user/email/exist",
        "/css/**",
        "/images/**",
        "/js/**"
    );

    private final AuthenticationService authenticationService;
    private final CookieUtil cookieUtil;
    private final FilterHelper filterHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("AuthFilter");
        String path = request.getRequestURI();
        log.debug("Request arrived: {}", path);
        if (pathMatcher.match("/logout", path)) {
            logout(request, response);
        } else if (isAllowedPath(path)) {
            log.debug("Allowed path: {}", path);
            filterChain.doFilter(request, response);
        } else if (isAuthenticated(request)) {
            log.debug("Needs service: {}", path);
            filterChain.doFilter(request, response);
        } else {
            filterHelper.handleUnauthorized(request, response, PageController.INDEX_MAPPING);
        }
    }

    private boolean isAllowedPath(String path) {
        return allowedUris.stream().anyMatch(allowedPath -> pathMatcher.match(allowedPath, path));
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Logging out...");
        String accessTokenId = cookieUtil.getCookie(request, COOKIE_ACCESS_TOKEN);
        String userId = cookieUtil.getCookie(request, COOKIE_USER_ID);
        authenticationService.logout(userId, accessTokenId);
        log.info("Successfully logged out.");
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        log.debug("Authenticating...");
        String accessTokenId = cookieUtil.getCookie(request, COOKIE_ACCESS_TOKEN);
        String userIdValue = cookieUtil.getCookie(request, COOKIE_USER_ID);

        if (accessTokenId == null || userIdValue == null) {
            log.warn("Cookies not found.");
            return false;
        }

        return authenticationService.isAuthenticated(userIdValue, accessTokenId);
    }
}
