package bookmarks.common.encryption;

import org.springframework.stereotype.Component;

import bookmarks.common.encryption.base.AbstractEncryptor;
import bookmarks.common.encryption.base.DefaultEncryptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class StringEncryptor extends AbstractEncryptor<String> {

    @Override
    protected String encrypt(String entity, String key) {
        DefaultEncryptor encryption = new DefaultEncryptor(key);
        return encryption.encrypt(entity);
    }

    @Override
    protected String decrypt(String entity, String key) {
        DefaultEncryptor decryption = new DefaultEncryptor(key);
        return decryption.decrypt(entity);
    }
}
