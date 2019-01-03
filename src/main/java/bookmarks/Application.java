package bookmarks;

import com.github.saphyra.authservice.EnableAuthService;
import com.github.saphyra.encryption.EnableEncryption;
import com.github.saphyra.exceptionhandling.EnableExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableAuthService
@EnableEncryption
@EnableExceptionHandler
public class Application {
    public static ConfigurableApplicationContext APP_CTX;

    public static void main(String[] args) {
        APP_CTX = SpringApplication.run(Application.class, args);
    }
}
