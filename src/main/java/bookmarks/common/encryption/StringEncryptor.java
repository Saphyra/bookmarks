package bookmarks.common.encryption;

import bookmarks.common.encryption.base.EncryptorCache;
import org.springframework.stereotype.Component;

import bookmarks.common.encryption.base.AbstractEncryptor;
import bookmarks.common.encryption.base.DefaultEncryptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StringEncryptor extends AbstractEncryptor<String> {
    private final EncryptorCache encryptorCache = new EncryptorCache(DefaultEncryptor.EncryptionMode.ENCRYPT);
    private final EncryptorCache decryptorCache = new EncryptorCache(DefaultEncryptor.EncryptionMode.DECRYPT);

    @Override
    protected String encrypt(String entity, String key) {
        DefaultEncryptor encryption = encryptorCache.get(key);
        return encryption.encrypt(entity);
    }

    @Override
    protected String decrypt(String entity, String key) {
        DefaultEncryptor decryption = decryptorCache.get(key);
        return decryption.decrypt(entity);
    }
}
