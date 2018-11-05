package bookmarks.common.encryption.base;

import bookmarks.common.AbstractCache;
import bookmarks.common.encryption.base.encryptionbase.EncryptionFactory;
import bookmarks.common.encryption.base.encryptionbase.Encryptor;

public class EncryptorCache extends AbstractCache<String, Encryptor> {

    @Override
    public Encryptor get(String key) {
        return get(key, () -> EncryptionFactory.getEncryptor(key));
    }
}
