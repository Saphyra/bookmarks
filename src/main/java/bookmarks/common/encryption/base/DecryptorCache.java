package bookmarks.common.encryption.base;

import bookmarks.common.AbstractCache;
import bookmarks.common.encryption.base.encryptionbase.Decryptor;
import bookmarks.common.encryption.base.encryptionbase.EncryptionFactory;

public class DecryptorCache extends AbstractCache<String, Decryptor> {

    @Override
    public Decryptor get(String key) {
        return get(key, () -> EncryptionFactory.getDecryptor(key));
    }
}
