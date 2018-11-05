package bookmarks.common.encryption.base;

import bookmarks.common.AbstractCache;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EncryptorCache extends AbstractCache<String, DefaultEncryptor> {
    private final DefaultEncryptor.EncryptionMode encryptionMode;
    @Override
    public DefaultEncryptor get(String key) {
        return get(key, () -> new DefaultEncryptor(key, encryptionMode));
    }
}
