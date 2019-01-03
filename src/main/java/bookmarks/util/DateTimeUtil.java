package bookmarks.util;

import com.github.saphyra.converter.ConverterBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@Slf4j
public class DateTimeUtil extends ConverterBase<Long, LocalDateTime> {
    public static final Integer EXPIRATION_TIME_IN_HOURS = 8;

    public LocalDateTime getExpirationDate() {
        LocalDateTime expirationDate = now();
        return expirationDate.minusHours(EXPIRATION_TIME_IN_HOURS);
    }

    @Override
    public LocalDateTime processEntityConversion(Long entity) {
        if (entity == null) {
            return null;
        }
        return LocalDateTime.ofEpochSecond(entity, 0, ZoneOffset.UTC);
    }

    @Override
    public Long processDomainConversion(LocalDateTime domain) {
        if (domain == null) {
            return null;
        }
        return domain.toEpochSecond(ZoneOffset.UTC);
    }

    public LocalDateTime now() {
        return LocalDateTime.now(ZoneOffset.UTC).withNano(0);
    }
}
