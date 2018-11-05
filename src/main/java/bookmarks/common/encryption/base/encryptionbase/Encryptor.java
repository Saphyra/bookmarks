package bookmarks.common.encryption.base.encryptionbase;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class Encryptor extends AbstractEncryption {
    public Encryptor(String password) {
        super(password, EncryptionMode.ENCRYPT);
    }

    public String encryptEntity(String text) {
        try {
            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            byte[] base64 = Base64.encodeBase64(encrypted);
            return new String(base64, StandardCharsets.UTF_8);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            log.debug("Error encryping value: {}", text);
            throw new RuntimeException(e);
        }
    }
}
