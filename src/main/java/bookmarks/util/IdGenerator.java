package bookmarks.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class IdGenerator {
    public String getRandomId(){
        return UUID.randomUUID().toString();
    }
}
