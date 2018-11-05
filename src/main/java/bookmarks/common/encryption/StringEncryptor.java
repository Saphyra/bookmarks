package bookmarks.common.encryption;

import bookmarks.common.encryption.base.*;
import bookmarks.common.encryption.base.encryptionbase.Decryptor;
import bookmarks.common.encryption.base.encryptionbase.Encryptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StringEncryptor extends AbstractEncryptor<String> {
    private final EncryptorCache encryptorCache = new EncryptorCache();
    private final DecryptorCache decryptorCache = new DecryptorCache();

    @Override
    protected String encrypt(String entity, String key) {
        Encryptor encryption = encryptorCache.get(key);
        return encryption.encryptEntity(entity);
    }

    @Override
    protected String decrypt(String entity, String key) {
        Decryptor decryption = decryptorCache.get(key);
        return decryption.decryptEntity(entity);
    }
}
