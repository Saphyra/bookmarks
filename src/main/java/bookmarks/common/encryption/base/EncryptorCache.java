package bookmarks.common.encryption.base;

import bookmarks.common.AbstractCache;

public class EncryptorCache extends AbstractCache<String, DefaultEncryptor> {
    @Override
    public DefaultEncryptor get(String key) {
        return get(key, () -> new DefaultEncryptor(key));
    }
}
