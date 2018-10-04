package bookmarks.util;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CookieUtil {
    public String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookieArray = request.getCookies();
        if(cookieArray == null){
            return null;
        }
        Optional<Cookie> cookie = Arrays.stream(cookieArray)
            .filter(c -> c.getName().equals(name))
            .findAny();
        return cookie.map(Cookie::getValue).orElse(null);
    }

    public void setCookie(HttpServletResponse response, String name, String value, int expiration){
        response.addCookie(createCookie(name, value, expiration));
    }

    private Cookie createCookie(String name, String value, int expiration){
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(expiration);
        return cookie;
    }
}
