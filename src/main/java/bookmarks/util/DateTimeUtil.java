package bookmarks.util;

import com.github.saphyra.converter.ConverterBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
@Slf4j
public class DateTimeUtil extends ConverterBase<Long, OffsetDateTime> {
    public static final Integer EXPIRATION_TIME_IN_HOURS = 8;

    public OffsetDateTime getExpirationDate() {
        OffsetDateTime expirationDate = now();
        return expirationDate.minusHours(EXPIRATION_TIME_IN_HOURS);
    }

    @Override
    public OffsetDateTime processEntityConversion(Long entity) {
        if (entity == null) {
            return null;
        }
        return OffsetDateTime.of(LocalDateTime.ofEpochSecond(entity, 0, ZoneOffset.UTC), ZoneOffset.UTC);
    }

    @Override
    public Long processDomainConversion(OffsetDateTime domain) {
        if (domain == null) {
            return null;
        }
        return domain.toEpochSecond();
    }

    public OffsetDateTime now() {
        return OffsetDateTime.now(ZoneOffset.UTC).withNano(0);
    }
}
