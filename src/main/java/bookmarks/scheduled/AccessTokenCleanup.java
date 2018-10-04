package bookmarks.scheduled;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import bookmarks.dataaccess.AccessTokenDao;
import bookmarks.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class AccessTokenCleanup {
    private final AccessTokenDao accessTokenDao;
    private final DateTimeUtil dateTimeUtil;

    @Scheduled(cron = "0 * * * * *")
    public void deleteOutDatedTokens() {
        log.info("Deleting outdated access tokens...");
        LocalDateTime expiration = dateTimeUtil.getExpirationDate();
        accessTokenDao.deleteExpired(dateTimeUtil.convertDomain(expiration));
        log.info("Outdated access tokens successfully deleted.");
    }
}
